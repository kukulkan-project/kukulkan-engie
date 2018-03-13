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
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addContentType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addMetaData;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.createJavaProperty;
import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;

import java.util.ArrayList;
import java.util.Optional;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.AssociationFieldContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.BlobFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.BooleanFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.CardinalityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.DateFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.EntityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.NumericFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.PrimitiveFieldContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.StringFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParserBaseVisitor;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KukulkanGrammarVisitor implements the.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarSemanticAnalyzer extends kukulkanParserBaseVisitor<VisitorContext> {

    /**
     * The logger class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarSemanticAnalyzer.class);

    /** The vctx. */
    private final VisitorContext vctx = new VisitorContext(new ArrayList<>());

    /** The entity. */
    private Entity entity = null;

    private PrimitiveFieldContext pfc = null;

    /** The property name. */
    private String propertyName = null;

    /** The java property. */
    private JavaProperty javaProperty = null;

    /** The constraint. */
    private Constraint constraint = null;

    private ProjectConfiguration pConf = null;

    public GrammarSemanticAnalyzer(ProjectConfiguration pConf) {
        this.pConf = pConf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitEntity(mx.
     * infotec.dads.kukulkan.grammar.kukulkanParser.EntityContext)
     */
    @Override
    public VisitorContext visitEntity(EntityContext ctx) {
        entity = Entity.createDomainModelElement();
        addMetaData(ctx, entity, pConf.getDatabase().getDatabaseType());
        getVctx().getElements().add(entity);
        return super.visitEntity(ctx);
    }

    @Override
    public VisitorContext visitPrimitiveField(PrimitiveFieldContext ctx) {
        pfc = ctx;
        propertyName = ctx.id.getText();
        constraint = new Constraint();
        super.visitChildren(ctx);
        javaProperty.setConstraint(constraint);
        return vctx;
    }

    @Override
    public VisitorContext visitAssociationField(AssociationFieldContext ctx) {
        LOGGER.debug(ctx.getText());
        return super.visitAssociationField(ctx);
    }

    @Override
    public VisitorContext visitCardinality(CardinalityContext ctx) {
        LOGGER.debug(ctx.getText());
        return super.visitCardinality(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitStringFieldType
     * (mx.infotec.dads.kukulkan.grammar.kukulkanParser.StringFieldTypeContext)
     */
    @Override
    public VisitorContext visitStringFieldType(StringFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional.of(GrammarFieldTypeMapping.getMap().get(ctx.name.getText()));
        processFieldType(optional);
        return super.visitStringFieldType(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitDateFieldType(
     * mx.infotec.dads.kukulkan.grammar.kukulkanParser.DateFieldTypeContext)
     */
    @Override
    public VisitorContext visitDateFieldType(DateFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional
                .of(GrammarFieldTypeMapping.getMap().get(getDateType(ctx.dateTypes())));
        processFieldType(optional);
        return super.visitDateFieldType(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#
     * visitNumericFieldType(mx.infotec.dads.kukulkan.grammar.kukulkanParser.
     * NumericFieldTypeContext)
     */
    @Override
    public VisitorContext visitNumericFieldType(NumericFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional
                .of(GrammarFieldTypeMapping.getMap().get(getNumericType(ctx.numericTypes())));
        processFieldType(optional);
        return super.visitNumericFieldType(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitBlobFieldType(
     * mx.infotec.dads.kukulkan.grammar.kukulkanParser.BlobFieldTypeContext)
     */
    @Override
    public VisitorContext visitBlobFieldType(BlobFieldTypeContext ctx) {
        Optional<GrammarFieldType> optional = Optional.of(GrammarFieldTypeMapping.getMap().get(ctx.name.getText()));
        processFieldType(optional);
        return super.visitBlobFieldType(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#
     * visitBooleanFieldType(mx.infotec.dads.kukulkan.grammar.kukulkanParser.
     * BooleanFieldTypeContext)
     */
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
        entity.setHasNotNullElements(true);
        entity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        return super.visitChildren(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#
     * visitPatternValidator(mx.infotec.dads.kukulkan.grammar.kukulkanParser.
     * PatternValidatorContext)
     */
    @Override
    public VisitorContext visitPatternValidator(kukulkanParser.PatternValidatorContext ctx) {
        constraint.setPattern(ctx.PATTERN_VALUE().getText().substring(1, ctx.PATTERN_VALUE().getText().length() - 1));
        entity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        return super.visitChildren(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitMinValidator(mx
     * .infotec.dads.kukulkan.grammar.kukulkanParser.MinValidatorContext)
     */
    @Override
    public VisitorContext visitMinValidator(kukulkanParser.MinValidatorContext ctx) {
        constraint.setMin(ctx.NUMERIC_VALUE().getText());
        entity.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
        javaProperty.setSizeValidation(true);
        return super.visitChildren(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitMaxValidator(mx
     * .infotec.dads.kukulkan.grammar.kukulkanParser.MaxValidatorContext)
     */
    @Override
    public VisitorContext visitMaxValidator(kukulkanParser.MaxValidatorContext ctx) {
        constraint.setMax(ctx.NUMERIC_VALUE().getText());
        entity.setHasConstraints(true);
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
            entity.addProperty(javaProperty);
            addContentType(entity, propertyName,pConf.getDatabase().getDatabaseType(), grammarPropertyType);
            GrammarMapping.addImports(entity.getImports(), javaProperty);
            DataBaseMapping.fillModelMetaData(entity, javaProperty);
        }
    }
}
