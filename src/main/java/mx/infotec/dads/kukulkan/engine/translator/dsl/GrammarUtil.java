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

import static mx.infotec.dads.kukulkan.engine.util.DataBaseMapping.createDefaultPrimaryKey;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.grammar.kukulkanLexer;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.EntityContext;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.EntityFieldContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelElement;
import mx.infotec.dads.kukulkan.metamodel.util.InflectorProcessor;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;
import mx.infotec.dads.kukulkan.metamodel.util.SchemaPropertiesParser;

/**
 * Grammar Util, It is used to performe common operation in the grammar.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class GrammarUtil {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarUtil.class);

    /**
     * Instantiates a new grammar util.
     */
    private GrammarUtil() {

    }

    /**
     * Gets the domain model context.
     *
     * @param file the file
     * @return the domain model context
     */
    public static kukulkanParser.DomainModelContext getDomainModelContext(String file) {
        try {
            LOGGER.debug("Interpreting file {}", file);
            kukulkanLexer lexer;
            lexer = new kukulkanLexer(new ANTLRFileStream(file));
            return getDomainModelContext(lexer);
        } catch (IOException e) {
            throw new MetaModelException("getDomainModelContext Error: ", e);
        }
    }

    /**
     * Gets the domain model context.
     *
     * @param file the file
     * @param isText the is text
     * @return the domain model context
     */
    public static kukulkanParser.DomainModelContext getDomainModelContext(String file, boolean isText) {
        if (isText) {
            try {
                LOGGER.debug("Interpreting file {}", file);
                kukulkanLexer lexer;
                lexer = new kukulkanLexer(new ANTLRInputStream(file));
                return getDomainModelContext(lexer);
            } catch (Exception e) {
                throw new MetaModelException("getDomainModelContext Error: ", e);
            }
        } else {
            return getDomainModelContext(file);
        }
    }

    /**
     * Gets the domain model context.
     *
     * @param lexer the lexer
     * @return the domain model context
     */
    public static kukulkanParser.DomainModelContext getDomainModelContext(kukulkanLexer lexer) {
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        kukulkanParser parser = new kukulkanParser(tokens);
        return parser.domainModel();
    }

    /**
     * Gets the kukulkan lexer.
     *
     * @param file the file
     * @return the kukulkan lexer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static kukulkanLexer getKukulkanLexer(String file) throws IOException {
        return new kukulkanLexer(new ANTLRFileStream(file));
    }

    /**
     * Gets the kukulkan lexer.
     *
     * @param is the is
     * @return the kukulkan lexer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static kukulkanLexer getKukulkanLexer(InputStream is) throws IOException {
        return new kukulkanLexer(new ANTLRInputStream(is));
    }

    /**
     * Adds the meta data.
     *
     * @param entity the entity
     * @param dme the dme
     */
    public static void addMetaData(EntityContext entity, DomainModelElement dme) {
        String singularName = InflectorProcessor.getInstance().singularize(entity.name.getText());
        dme.setTableName(entity.name.getText().toUpperCase());
        dme.setName(entity.name.getText());
        dme.setCamelCaseFormat(SchemaPropertiesParser.parseToPropertyName(singularName));
        dme.setCamelCasePluralFormat(InflectorProcessor.getInstance().pluralize(dme.getCamelCaseFormat()));
        dme.setPrimaryKey(createDefaultPrimaryKey());
    }

    /**
     * Adds the content type.
     *
     * @param dme the dme
     * @param propertyName the property name
     * @param propertyType the property type
     */
    public static void addContentType(DomainModelElement dme, String propertyName, GrammarPropertyType propertyType) {
        if (propertyType.isBinary()) {
            dme.addProperty(createContentTypeProperty(propertyName));
        }
    }

    /**
     * Creates the java property.
     *
     * @param field the field
     * @param propertyName the property name
     * @param propertyType the property type
     * @return the java property
     */
    public static JavaProperty createJavaProperty(EntityFieldContext field, String propertyName,
            GrammarPropertyType propertyType) {
        return JavaProperty.builder().withName(propertyName).withType(propertyType.getJavaName())
                .withColumnName(propertyName).withColumnType(propertyType.getGrammarName())
                .withQualifiedName(propertyType.getJavaQualifiedName()).isNullable(true).isPrimaryKey(false)
                .isIndexed(false).isLocalDate(propertyType.getJavaEquivalentClass().equals(LocalDate.class))
                .isZoneDateTime(propertyType.getJavaEquivalentClass().equals(ZonedDateTime.class))
                .isInstance(propertyType.getJavaEquivalentClass().equals(Instant.class))
                .isLargeObject(propertyType.isLargeObject()).addType(field.type)
                .withJavaEquivalentClass(propertyType.getJavaEquivalentClass()).build();
    }

    /**
     * Creates the content type property.
     *
     * @param propertyName the property name
     * @return the java property
     */
    public static JavaProperty createContentTypeProperty(String propertyName) {
        return JavaProperty.builder().withName(propertyName + "ContentType").withType("String")
                .withColumnName(propertyName + "ContentType").withColumnType("TextBlob")
                .withQualifiedName("java.lang.String").isNullable(true).isPrimaryKey(false).isIndexed(false)
                .isLargeObject(false).isLiteral(true).withJavaEquivalentClass(String.class).build();
    }
}
