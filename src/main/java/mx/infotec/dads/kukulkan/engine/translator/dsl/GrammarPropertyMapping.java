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

import static mx.infotec.dads.kukulkan.engine.translator.dsl.SuperColumnType.BINARY_TYPE;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.SuperColumnType.BOOLEAN_TYPE;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.SuperColumnType.LITERAL_TYPE;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.SuperColumnType.NUMBER_TYPE;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.SuperColumnType.TIME_TYPE;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import mx.infotec.dads.kukulkan.grammar.kukulkanParser.DateTypesContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.FieldTypeContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.NumericTypesContext;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * GrammarPropertyMapping.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarPropertyMapping {

    /** The Constant map. */
    private static final HashMap<String, GrammarPropertyType> map;
    static {
        map = new HashMap<>();
        /*
         * Literal
         */
        getMap().put("String", new GrammarPropertyTypeImpl("String", LITERAL_TYPE));
        getMap().put("TextBlob", new GrammarPropertyTypeImpl("TextBlob", LITERAL_TYPE, String.class, true));

        /*
         * Numbers
         */
        getMap().put("Integer", new GrammarPropertyTypeImpl("Integer", NUMBER_TYPE, Integer.class));
        getMap().put("Long", new GrammarPropertyTypeImpl("Long", NUMBER_TYPE, Long.class));
        getMap().put("BigDecimal", new GrammarPropertyTypeImpl("BigDecimal", NUMBER_TYPE, BigDecimal.class));
        getMap().put("Float", new GrammarPropertyTypeImpl("Float", NUMBER_TYPE, Float.class));
        getMap().put("Double", new GrammarPropertyTypeImpl("Double", NUMBER_TYPE, Double.class));

        /*
         * Time based
         */
        getMap().put("Date", new GrammarPropertyTypeImpl("Date", TIME_TYPE, Date.class));
        getMap().put("LocalDate", new GrammarPropertyTypeImpl("LocalDate", TIME_TYPE, LocalDate.class));
        getMap().put("ZonedDateTime", new GrammarPropertyTypeImpl("ZonedDateTime", TIME_TYPE, ZonedDateTime.class));
        getMap().put("Instant", new GrammarPropertyTypeImpl("Instant", TIME_TYPE, Instant.class));

        /*
         * Booleans
         */
        getMap().put("Boolean", new GrammarPropertyTypeImpl("Boolean", BOOLEAN_TYPE, boolean.class));

        /*
         * Blobs
         */
        getMap().put("Blob", new GrammarPropertyTypeImpl("Blob", BINARY_TYPE, byte[].class, true));
        getMap().put("AnyBlob", new GrammarPropertyTypeImpl("AnyBlob", BINARY_TYPE, byte[].class, true));
        getMap().put("ImageBlob", new GrammarPropertyTypeImpl("ImageBlob", BINARY_TYPE, byte[].class, true));

    }

    /**
     * Instantiates a new grammar property mapping.
     */
    private GrammarPropertyMapping() {
    }

    /**
     * Gets the property type.
     *
     * @param property the property
     * @return the property type
     */
    public static GrammarPropertyType getPropertyType(String property) {
        return getMap().get(property);
    }

    /**
     * Gets the property type.
     *
     * @param type the type
     * @return the property type
     */
    public static GrammarPropertyType getPropertyType(FieldTypeContext type) {
        Optional<GrammarPropertyType> optional = Optional
                .of(getMap().get(extractPropertyType(type).getFieldTypeName()));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new MetaModelException("Property Not Found" + type.getText());
        }
    }

    /**
     * Extract property type.
     *
     * @param type the type
     * @return the grammar property
     */
    public static GrammarProperty extractPropertyType(FieldTypeContext type) {
        if (type.stringFieldType() != null) {
            return new GrammarProperty(null, type.stringFieldType().name.getText());
        } else if (type.numericFieldType() != null) {
            return new GrammarProperty(null, getNumericType(type.numericFieldType().numericTypes()));
        } else if (type.booleanFieldType() != null) {
            return new GrammarProperty(null, type.booleanFieldType().name.getText());
        } else if (type.dateFieldType() != null) {
            return new GrammarProperty(null, getDateType(type.dateFieldType().dateTypes()));
        } else if (type.blobFieldType() != null) {
            return new GrammarProperty(null, type.blobFieldType().name.getText());
        } else {
            throw new MetaModelException("Property type not found for: " + type.getText());
        }
    }

    /**
     * Gets the numeric type.
     *
     * @param numericTypes the numeric types
     * @return the numeric type
     */
    public static String getNumericType(NumericTypesContext numericTypes) {
        if (numericTypes.LONG() != null) {
            return numericTypes.LONG().getText();
        } else if (numericTypes.FLOAT() != null) {
            return numericTypes.FLOAT().getText();
        } else if (numericTypes.INTEGER() != null) {
            return numericTypes.INTEGER().getText();
        } else if (numericTypes.BIG_DECIMAL() != null) {
            return numericTypes.BIG_DECIMAL().getText();
        } else if (numericTypes.DOUBLE() != null) {
            return numericTypes.DOUBLE().getText();
        } else {
            throw new MetaModelException("Numeric Type Not Found for: " + numericTypes.getText());
        }
    }

    /**
     * Gets the date type.
     *
     * @param dataTypes the data types
     * @return the date type
     */
    public static String getDateType(DateTypesContext dataTypes) {
        if (dataTypes.DATE() != null) {
            return dataTypes.DATE().getText();
        } else if (dataTypes.INSTANT() != null) {
            return dataTypes.INSTANT().getText();
        } else if (dataTypes.LOCAL_DATE() != null) {
            return dataTypes.LOCAL_DATE().getText();
        } else if (dataTypes.ZONED_DATETIME() != null) {
            return dataTypes.ZONED_DATETIME().getText();
        } else {
            throw new MetaModelException("Date Type Not Found for: " + dataTypes.getText());
        }
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public static HashMap<String, GrammarPropertyType> getMap() {
        return map;
    }
}
