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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GrammarPropertyTypeImpl.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarFieldTypeImpl implements GrammarFieldType {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant logger. */
    public static final Logger logger = LoggerFactory.getLogger(GrammarFieldTypeImpl.class);

    /** The name. */
    private final FieldType fieldType;
    
    /** The super column type. */
    private final SuperColumnType superColumnType;
    
    /** The java type. */
    private final Class<?> javaType;
    
    /** The large object. */
    private final boolean _largeObject;

    /**
     * Instantiates a new grammar property type impl.
     *
     * @param name the name
     * @param superColumnType the super column type
     */
    public GrammarFieldTypeImpl(FieldType fieldType, SuperColumnType superColumnType) {
        this(fieldType, superColumnType, null);
    }

    /**
     * Instantiates a new grammar property type impl.
     *
     * @param name the name
     * @param superColumnType the super column type
     * @param javaType the java type
     */
    public GrammarFieldTypeImpl(FieldType fieldType, SuperColumnType superColumnType, Class<?> javaType) {
        this(fieldType, superColumnType, javaType, false);
    }

    /**
     * Instantiates a new grammar property type impl.
     *
     * @param name the name
     * @param superColumnType the super column type
     * @param javaType the java type
     * @param largeObject the large object
     */
    public GrammarFieldTypeImpl(FieldType fieldType, SuperColumnType superColumnType, Class<?> javaType,
            boolean largeObject) {
        if (fieldType == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (superColumnType == null) {
            throw new IllegalArgumentException("SuperColumnType cannot be null");
        }
        this.fieldType = fieldType;
        this.superColumnType = superColumnType;
        if (javaType == null) {
            this.javaType = superColumnType.getJavaEquivalentClass();
        } else {
            this.javaType = javaType;
        }
        this._largeObject = largeObject;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#isBoolean()
     */
    public boolean isBoolean() {
        return this.superColumnType == SuperColumnType.BOOLEAN_TYPE;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#isBinary()
     */
    public boolean isBinary() {
        return this.superColumnType == SuperColumnType.BINARY_TYPE;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#isNumber()
     */
    public boolean isNumber() {
        return this.superColumnType == SuperColumnType.NUMBER_TYPE;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#isTimeBased()
     */
    public boolean isTimeBased() {
        return this.superColumnType == SuperColumnType.TIME_TYPE;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#isLiteral()
     */
    public boolean isLiteral() {
        return this.superColumnType == SuperColumnType.LITERAL_TYPE;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#isLargeObject()
     */
    public boolean isLargeObject() {
        return this._largeObject;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#getJavaEquivalentClass()
     */
    public Class<?> getJavaEquivalentClass() {
        return this.javaType;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#getSuperType()
     */
    public SuperColumnType getSuperType() {
        return this.superColumnType;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#getGrammarName()
     */
    @Override
    public FieldType getFieldType() {
        return this.fieldType;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#getJavaName()
     */
    @Override
    public String getJavaName() {
        return this.javaType.getSimpleName();
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarPropertyType#getJavaQualifiedName()
     */
    @Override
    public String getJavaQualifiedName() {
        return this.javaType.getCanonicalName();
    }
}
