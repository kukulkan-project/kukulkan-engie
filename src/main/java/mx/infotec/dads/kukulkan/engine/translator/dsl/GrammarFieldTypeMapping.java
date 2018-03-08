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

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.DateTypesContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.NumericTypesContext;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * GrammarPropertyMapping.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarFieldTypeMapping {

    /** The Constant map. */
    private static final HashMap<String, GrammarFieldType> map;
    static {
        map = new HashMap<>();
        /*
         * Literal
         */
        getMap().put(FieldType.STRING.text(), new GrammarFieldTypeImpl(FieldType.STRING, LITERAL_TYPE));
        getMap().put(FieldType.TEXT_BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));

        /*
         * Numbers
         */
        getMap().put(FieldType.INTEGER.text(), new GrammarFieldTypeImpl(FieldType.INTEGER, NUMBER_TYPE, Integer.class));
        getMap().put(FieldType.LONG.text(), new GrammarFieldTypeImpl(FieldType.LONG, NUMBER_TYPE, Long.class));
        getMap().put(FieldType.BIG_DECIMAL.text(),
                new GrammarFieldTypeImpl(FieldType.BIG_DECIMAL, NUMBER_TYPE, BigDecimal.class));
        getMap().put(FieldType.FLOAT.text(), new GrammarFieldTypeImpl(FieldType.FLOAT, NUMBER_TYPE, Float.class));
        getMap().put(FieldType.DOUBLE.text(), new GrammarFieldTypeImpl(FieldType.DOUBLE, NUMBER_TYPE, Double.class));

        /*
         * Time based
         */
        getMap().put(FieldType.DATE.text(), new GrammarFieldTypeImpl(FieldType.DATE, TIME_TYPE, Date.class));
        getMap().put(FieldType.LOCAL_DATE.text(),
                new GrammarFieldTypeImpl(FieldType.LOCAL_DATE, TIME_TYPE, LocalDate.class));
        getMap().put(FieldType.ZONED_DATETIME.text(),
                new GrammarFieldTypeImpl(FieldType.ZONED_DATETIME, TIME_TYPE, ZonedDateTime.class));
        getMap().put(FieldType.INSTANT.text(), new GrammarFieldTypeImpl(FieldType.INSTANT, TIME_TYPE, Instant.class));

        /*
         * Booleans
         */
        getMap().put(FieldType.BOOLEAN_TYPE.text(),
                new GrammarFieldTypeImpl(FieldType.BOOLEAN_TYPE, BOOLEAN_TYPE, boolean.class));

        /*
         * Blobs
         */
        getMap().put(FieldType.BLOB.text(), new GrammarFieldTypeImpl(FieldType.BLOB, BINARY_TYPE, byte[].class, true));
        getMap().put(FieldType.ANY_BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.ANY_BLOB, BINARY_TYPE, byte[].class, true));
        getMap().put(FieldType.IMAGE_BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.IMAGE_BLOB, BINARY_TYPE, byte[].class, true));

    }

    /**
     * Instantiates a new grammar property mapping.
     */
    private GrammarFieldTypeMapping() {
    }

    /**
     * Gets the property type.
     *
     * @param property
     *            the property
     * @return the property type
     */
    public static GrammarFieldType getPropertyType(String property) {
        return getMap().get(property);
    }

    /**
     * Gets the numeric type.
     *
     * @param numericTypes
     *            the numeric types
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
     * @param dataTypes
     *            the data types
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

    public static void configurateGrammarFieldType(GrammarFieldType propertyType, JavaProperty javaProperty) {
        switch (propertyType.getFieldType()) {
        case LOCAL_DATE:
            javaProperty.setLocalDate(true);
            break;
        case ZONED_DATETIME:
            javaProperty.setZoneDateTime(true);
            break;
        case INSTANT:
            javaProperty.setInstant(true);
            break;
        case BIG_DECIMAL:
            javaProperty.setBigDecimal(true);
            break;
        case ANY_BLOB:
            javaProperty.setAnyBlob(true);
            break;
        case IMAGE_BLOB:
            javaProperty.setImageBlob(true);
            break;
        case BLOB:
            javaProperty.setBlob(true);
            break;
        default:
            break;
        }
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public static HashMap<String, GrammarFieldType> getMap() {
        return map;
    }
}
