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

import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.GrammarFieldType;

/**
 * GrammarProperty is a Wrapper.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class GrammarField {

    /** The field type name. */
    private String fieldTypeName;

    /** The property type. */
    private GrammarFieldType fieldType;
    
    /** The constraint. */
    private Constraint constraint;

    /**
     * Instantiates a new grammar property.
     */
    public GrammarField() {
    }

    /**
     * Instantiates a new grammar property.
     *
     * @param constraint the constraint
     * @param fieldTypeName the field type name
     */
    public GrammarField(Constraint constraint, String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
        this.constraint = constraint;
    }

    /**
     * Gets the property type.
     *
     * @return the property type
     */
    public GrammarFieldType getFieldType() {
        return fieldType;
    }

    /**
     * Sets the property type.
     *
     * @param propertyType the new property type
     */
    public void setFieldType(GrammarFieldType propertyType) {
        this.fieldType = propertyType;
    }

    /**
     * Gets the constraint.
     *
     * @return the constraint
     */
    public Constraint getConstraint() {
        return constraint;
    }

    /**
     * Sets the constraint.
     *
     * @param constraint the new constraint
     */
    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    /**
     * Gets the field type name.
     *
     * @return the field type name
     */
    public String getFieldTypeName() {
        return fieldTypeName;
    }

    /**
     * Sets the field type name.
     *
     * @param fieldTypeName the new field type name
     */
    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

}
