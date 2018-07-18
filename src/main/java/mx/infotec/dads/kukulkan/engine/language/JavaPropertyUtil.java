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
package mx.infotec.dads.kukulkan.engine.language;

import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;

/**
 * PropertyHolder Class that is used for hold the properties of a table.
 *
 * @author Daniel Cortes Pichardo
 */
public class JavaPropertyUtil {

    private JavaPropertyUtil() {

    }

    /**
     * Creates the java property.
     *
     * @param field
     *            the field
     * @param propertyName
     *            the property name
     * @param propertyType
     *            the property type
     * @return the java property
     */
    public static JavaProperty createJavaProperty(String propertyName, GrammarFieldType propertyType,
            DatabaseType dbType) {
        return JavaProperty.builder().withName(propertyName).withPropertyType(propertyType)
                .withColumnName(toDataBaseNameConvention(dbType, propertyName)).isNullable(true).isPrimaryKey(false)
                .isIndexed(false).build();
    }

}
