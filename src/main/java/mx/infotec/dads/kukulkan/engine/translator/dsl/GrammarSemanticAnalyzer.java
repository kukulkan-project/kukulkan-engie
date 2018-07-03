/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.kukulkan.engine.translator.dsl;

import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeMapping.getDateType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeMapping.getNumericType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping.resolveAssociationType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addContentType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.createJavaProperty;
import static mx.infotec.dads.kukulkan.engine.util.CoreEntitesUtil.CORE_USER;
import static mx.infotec.dads.kukulkan.engine.util.CoreEntitesUtil.ENTITY_USER;
import static mx.infotec.dads.kukulkan.engine.util.CoreEntitesUtil.determineUserCorePhysicalName;
import static mx.infotec.dads.kukulkan.engine.util.DataBaseMapping.createDefaultPrimaryKey;
import static mx.infotec.dads.kukulkan.engine.util.DataBaseMapping.createIdJavaProperty;
import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;
import static mx.infotec.dads.kukulkan.metamodel.util.SchemaPropertiesParser.parseToHyphens;
import static mx.infotec.dads.kukulkan.metamodel.util.SchemaPropertiesParser.parseToLowerCaseFirstChar;

import java.util.ArrayList;
import java.util.Optional;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.AssociationFieldContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.BlobFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.BooleanFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.CardinalityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.CoreEntityAssociationFieldContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.DateFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.EntityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.NumericFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.PrimitiveFieldContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.PrimitiveFieldMarkersContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.StringFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.UserCardinalityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParserBaseVisitor;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.SchemaPropertiesParser;

/**
 * KukulkanGrammarVisitor implements the.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarSemanticAnalyzer extends kukulkanParserBaseVisitor<VisitorContext> {

    private static final String JAVA_UTIL_HASH_SET = "java.util.HashSet";
    private static final String JSON_IGNORE = "com.fasterxml.jackson.annotation.JsonIgnore";
    private static final String JAVA_UTIL_COLLECTION = "java.util.Set";

    /** The vctx. */
    private final VisitorContext vctx = new VisitorContext(new ArrayList<>());

    private EntityHolder entityHolder = new EntityHolder();

    /** The entity. */
    private Entity sourceEntity = null;

    private Entity targetEntity = null;

    private EntityAssociation entityAssociation = null;

    private PrimitiveFieldContext pfc = null;

    /** The property name. */
    private String propertyName = null;

    /** The java property. */
    private JavaProperty javaProperty = null;

    /** The constraint. */
    private Constraint constraint = null;

    private ProjectConfiguration pConf = null;

    private InflectorService inflectorService;

    private boolean isPropertyToShow = false;

    public GrammarSemanticAnalyzer(ProjectConfiguration pConf, InflectorService inflectorService) {
        this.pConf = pConf;
        this.inflectorService = inflectorService;
    }

    @Override
    public VisitorContext visitEntity(EntityContext ctx) {
        String entityName = ctx.name.getText();
        sourceEntity = entityHolder.getEntity(ctx.name.getText(), pConf.getDatabase().getDatabaseType());
        String tableName = ctx.tableName != null ? ctx.tableName.getText() : null;
        addMetaData(entityName, tableName, sourceEntity, pConf.getDatabase().getDatabaseType());
        getVctx().getElements().add(sourceEntity);
        return super.visitEntity(ctx);
    }

    @Override
    public VisitorContext visitPrimitiveFieldMarkers(PrimitiveFieldMarkersContext ctx) {
        if ("->".equals(ctx.getText()) && javaProperty.isString()) {
            isPropertyToShow = true;
        }
        return super.visitPrimitiveFieldMarkers(ctx);
    }

    @Override
    public VisitorContext visitPrimitiveField(PrimitiveFieldContext ctx) {
        pfc = ctx;
        propertyName = ctx.id.getText();
        constraint = new Constraint();
        super.visitChildren(ctx);
        javaProperty.setConstraint(constraint);
        setPropertyToShow();
        return vctx;
    }

    /**
     * Visit Association
     */
    @Override
    public VisitorContext visitAssociationField(AssociationFieldContext ctx) {
        targetEntity = entityHolder.getEntity(ctx.targetEntity.getText(), pConf.getDatabase().getDatabaseType());
        entityAssociation = new EntityAssociation(sourceEntity, targetEntity);
        entityAssociation.setToTargetPropertyName(ctx.id.getText());
        if (ctx.toSourcePropertyName != null) {
            entityAssociation.setBidirectional(true);
            entityAssociation.setToSourcePropertyName(ctx.toSourcePropertyName.getText());
        } else {
            entityAssociation.setBidirectional(false);
            entityAssociation
                    .setToSourcePropertyName(parseToLowerCaseFirstChar(entityAssociation.getSource().getName()));
        }
        return super.visitAssociationField(ctx);
    }

    @Override
    public VisitorContext visitCoreEntityAssociationField(CoreEntityAssociationFieldContext ctx) {
        String associableEntity = ctx.targetEntity.getText();
        if (CORE_USER.equals(associableEntity)) {
            targetEntity = Entity.createDomainModelElement();
            addMetaData(ENTITY_USER, determineUserCorePhysicalName(pConf), targetEntity,
                    pConf.getDatabase().getDatabaseType());
            entityAssociation = new EntityAssociation(sourceEntity, targetEntity);
            entityAssociation.setToTargetPropertyName(ctx.id.getText());
        }
        return super.visitCoreEntityAssociationField(ctx);
    }

    @Override
    public VisitorContext visitUserCardinality(UserCardinalityContext ctx) {
        genericVisitCardinality(ctx.getText());
        return super.visitUserCardinality(ctx);
    }

    @Override
    public VisitorContext visitCardinality(CardinalityContext ctx) {
        genericVisitCardinality(ctx.getText());
        return super.visitCardinality(ctx);
    }

    private void genericVisitCardinality(String type) {
        entityAssociation.setType(resolveAssociationType(sourceEntity, type));
        entityAssociation.setToTargetPropertyNamePlural(pluralize(entityAssociation.getToTargetPropertyName()));
        entityAssociation.setToSourcePropertyNamePlural(pluralize(entityAssociation.getToSourcePropertyName()));

        entityAssociation.setToTargetPropertyNameUnderscore(
                SchemaPropertiesParser.parseToUnderscore(entityAssociation.getToTargetPropertyName()));
        entityAssociation.setToSourcePropertyNameUnderscore(
                SchemaPropertiesParser.parseToUnderscore(entityAssociation.getToSourcePropertyName()));

        entityAssociation.setToTargetPropertyNameUnderscorePlural(
                SchemaPropertiesParser.parseToUnderscore(pluralize(entityAssociation.getToTargetPropertyName())));
        entityAssociation.setToSourcePropertyNameUnderscorePlural(
                SchemaPropertiesParser.parseToUnderscore(pluralize(entityAssociation.getToSourcePropertyName())));

        assignAssociation(sourceEntity, targetEntity, entityAssociation);
        resolveImports(sourceEntity, targetEntity, entityAssociation);
    }

    private void resolveImports(Entity sourceEntity, Entity targetEntity, EntityAssociation entityAssociation) {
        switch (entityAssociation.getType()) {
        case ONE_TO_ONE:
            break;
        case ONE_TO_MANY:
            sourceEntity.getImports().add(JAVA_UTIL_COLLECTION);
            sourceEntity.getImports().add(JAVA_UTIL_HASH_SET);
            sourceEntity.getImports().add(JSON_IGNORE);
            break;
        case MANY_TO_ONE:
            if (entityAssociation.isBidirectional()) {
                targetEntity.getImports().add(JAVA_UTIL_COLLECTION);
                targetEntity.getImports().add(JAVA_UTIL_HASH_SET);
            }
            break;
        case MANY_TO_MANY:
            sourceEntity.getImports().add(JAVA_UTIL_COLLECTION);
            sourceEntity.getImports().add(JAVA_UTIL_HASH_SET);
            if (entityAssociation.isBidirectional()) {
                targetEntity.getImports().add(JAVA_UTIL_COLLECTION);
                targetEntity.getImports().add(JAVA_UTIL_HASH_SET);
                targetEntity.getImports().add(JSON_IGNORE);
            }
            break;
        default:
            break;
        }
    }

    @Override
    public VisitorContext visitStringFieldType(StringFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional.of(GrammarFieldTypeMapping.getMap().get(ctx.name.getText()));
        processFieldType(optional);
        return super.visitStringFieldType(ctx);
    }

    @Override
    public VisitorContext visitDateFieldType(DateFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional
                .of(GrammarFieldTypeMapping.getMap().get(getDateType(ctx.dateTypes())));
        processFieldType(optional);
        return super.visitDateFieldType(ctx);
    }

    @Override
    public VisitorContext visitNumericFieldType(NumericFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional
                .of(GrammarFieldTypeMapping.getMap().get(getNumericType(ctx.numericTypes())));
        processFieldType(optional);
        return super.visitNumericFieldType(ctx);
    }

    @Override
    public VisitorContext visitBlobFieldType(BlobFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional.of(GrammarFieldTypeMapping.getMap().get(ctx.name.getText()));
        processFieldType(optional);
        return super.visitBlobFieldType(ctx);
    }

    @Override
    public VisitorContext visitBooleanFieldType(BooleanFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional.of(GrammarFieldTypeMapping.getMap().get(ctx.name.getText()));
        processFieldType(optional);
        return super.visitBooleanFieldType(ctx);
    }

    /**
     * Visit Constraints.
     *
     * @param ctx
     *            the ctx
     * @return the visitor context
     */
    @Override
    public VisitorContext visitRequiredValidator(kukulkanParser.RequiredValidatorContext ctx) {
        constraint.setNullable(false);
        sourceEntity.setHasNotNullElements(true);
        sourceEntity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        return super.visitChildren(ctx);
    }

    @Override
    public VisitorContext visitPatternValidator(kukulkanParser.PatternValidatorContext ctx) {
        constraint.setPattern(ctx.PATTERN_VALUE().getText().substring(1, ctx.PATTERN_VALUE().getText().length() - 1));
        sourceEntity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        return super.visitChildren(ctx);
    }

    @Override
    public VisitorContext visitMinValidator(kukulkanParser.MinValidatorContext ctx) {
        constraint.setMin(ctx.NUMERIC_VALUE().getText());
        sourceEntity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        javaProperty.setSizeValidation(true);
        return super.visitChildren(ctx);
    }

    @Override
    public VisitorContext visitMaxValidator(kukulkanParser.MaxValidatorContext ctx) {
        constraint.setMax(ctx.NUMERIC_VALUE().getText());
        sourceEntity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        javaProperty.setSizeValidation(true);
        return super.visitChildren(ctx);
    }

    /**
     * Gets the vctx.
     *
     * @return the vctx
     */
    public VisitorContext getVctx() {
        return vctx;
    }

    /**
     * Process field type.
     *
     * @param optional
     *            the optional
     */
    public void processFieldType(Optional<GrammarFieldType> optional) {
        if (optional.isPresent()) {
            GrammarFieldType grammarPropertyType = optional.get();
            javaProperty = createJavaProperty(pfc, propertyName, grammarPropertyType,
                    pConf.getDatabase().getDatabaseType());
            sourceEntity.addProperty(javaProperty);
            addContentType(sourceEntity, propertyName, pConf.getDatabase().getDatabaseType(), grammarPropertyType);
            GrammarMapping.addImports(sourceEntity.getImports(), javaProperty);
            DataBaseMapping.fillModelMetaData(sourceEntity, javaProperty);
        }
    }

    public void addMetaData(String entityName, String physicalName, Entity entity, DatabaseType dbType) {
        String singularName = singularize(entityName);
        if (singularName == null) {
            singularName = entityName;
        }
        if (physicalName == null || "".equals(physicalName)) {
            entity.setTableName(toDataBaseNameConvention(dbType, pluralize(entityName)));
        } else {
            entity.setTableName(physicalName);
        }
        entity.setUnderscoreName(SchemaPropertiesParser.parsePascalCaseToUnderscore(entity.getName()));
        entity.setName(entityName);
        entity.setCamelCaseFormat(SchemaPropertiesParser.parseToPropertyName(singularName));
        entity.setCamelCasePluralFormat(pluralize(entity.getCamelCaseFormat()));
        entity.setHyphensFormat(parseToHyphens(entity.getCamelCaseFormat()));
        entity.setHyphensPluralFormat(parseToHyphens(entity.getCamelCasePluralFormat()));
        entity.setPrimaryKey(createDefaultPrimaryKey(dbType));
        entity.setDisplayField(createIdJavaProperty());
    }

    private void assignAssociation(Entity sourceEntity, Entity targetEntity, EntityAssociation entityAssociation) {
        sourceEntity.addAssociation(entityAssociation);
        // if association is a cycle, then sourceEntity already have the
        // association so it is not necessary added to it
        if (!entityAssociation.isCycle()) {
            targetEntity.addAssociation(entityAssociation);
        }
    }

    private void setPropertyToShow() {
        if (isPropertyToShow) {
            sourceEntity.setDisplayField(javaProperty);
            isPropertyToShow = false;
        }
    }

    public String singularize(String word) {
        if (word == null) {
            return null;
        }
        String singularize = inflectorService.singularize(word);
        if (singularize == null) {
            return word;
        } else {
            return singularize;
        }
    }

    public String pluralize(String word) {
        if (word == null) {
            return null;
        }
        String pluralize = inflectorService.pluralize(word);
        if (pluralize == null) {
            return word;
        } else {
            return pluralize;
        }
    }
}
