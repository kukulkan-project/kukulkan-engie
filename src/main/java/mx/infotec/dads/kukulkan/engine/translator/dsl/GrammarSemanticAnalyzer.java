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

import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyMapping.getDateType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyMapping.getNumericType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addContentType;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addMetaData;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.createJavaProperty;

import java.util.ArrayList;
import java.util.Optional;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.BlobFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.BooleanFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.CardinalityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.DateFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.EntityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.EntityFieldContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.NumericFieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.StringFieldTypeContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * KukulkanGrammarVisitor implements the.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarSemanticAnalyzer extends kukulkanBaseVisitor<VisitorContext> {

    /** The vctx. */
    private final VisitorContext vctx = new VisitorContext(new ArrayList<Entity>());

    /** The dme. */
    private Entity dme = null;

    /** The efc. */
    private EntityFieldContext efc = null;

    /** The property name. */
    private String propertyName = null;

    /** The java property. */
    private JavaProperty javaProperty = null;

    /** The constraint. */
    private Constraint constraint = null;

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitEntity(mx.
     * infotec.dads.kukulkan.grammar.kukulkanParser.EntityContext)
     */
    @Override
    public VisitorContext visitEntity(EntityContext ctx) {
        dme = Entity.createDomainModelElement();
        addMetaData(ctx, dme);
        getVctx().getElements().add(dme);
        return super.visitEntity(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.grammar.kukulkanBaseVisitor#visitEntityField(mx.
     * infotec.dads.kukulkan.grammar.kukulkanParser.EntityFieldContext)
     */
    @Override
    public VisitorContext visitEntityField(EntityFieldContext ctx) {
        efc = ctx;
        propertyName = ctx.id.getText();
        constraint = new Constraint();
        super.visitEntityField(ctx);
        javaProperty.setConstraint(constraint);
        return vctx;
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
        Optional<GrammarPropertyType> optional = Optional.of(GrammarPropertyMapping.getMap().get(ctx.name.getText()));
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
        Optional<GrammarPropertyType> optional = Optional
                .of(GrammarPropertyMapping.getMap().get(getDateType(ctx.dateTypes())));
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
        Optional<GrammarPropertyType> optional = Optional
                .of(GrammarPropertyMapping.getMap().get(getNumericType(ctx.numericTypes())));
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
        Optional<GrammarPropertyType> optional = Optional.of(GrammarPropertyMapping.getMap().get(ctx.name.getText()));
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
        Optional<GrammarPropertyType> optional = Optional.of(GrammarPropertyMapping.getMap().get(ctx.name.getText()));
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
        dme.setHasNotNullElements(true);
        dme.setHasConstraints(true);
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
        dme.setHasConstraints(true);
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
        dme.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
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
        dme.setHasConstraints(true);
        javaProperty.setHasConstraints(true);
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
    public void processFieldType(Optional<GrammarPropertyType> optional) {
        if (optional.isPresent()) {
            GrammarPropertyType grammarPropertyType = optional.get();
            javaProperty = createJavaProperty(efc, propertyName, grammarPropertyType);
            dme.addProperty(javaProperty);
            addContentType(dme, propertyName, grammarPropertyType);
            GrammarMapping.addImports(dme.getImports(), javaProperty);
            DataBaseMapping.fillModelMetaData(dme, javaProperty);
        }
    }

    @Override
    public VisitorContext visitCardinality(CardinalityContext ctx) {
        System.out.println(ctx.getText());
        return super.visitCardinality(ctx);
    }
}
