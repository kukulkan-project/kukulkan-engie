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

import static mx.infotec.dads.kukulkan.engine.translator.SourceType.GRAMMAR;
import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.infotec.dads.kukulkan.dsl.kukulkan.DomainModel;
import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * Grammar Util, It is used to performe common operation in the grammar.
 *
 * @author Daniel Cortes Pichardo
 *
 */
public class GrammarUtil {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarUtil.class);

    /**
     * Instantiates a new grammar util.
     */
    private GrammarUtil() {

    }

    /**
     * Gets the domain model context.
     *
     * @param file
     *            the file
     * @return the domain model context
     */
    public static DomainModel getDomainModelAST(String file, ResourceSet resourceSet) {
        try {
            LOGGER.debug("Interpreting file {}", file);
            Resource resource = resourceSet.getResource(URI.createFileURI(file), true);
            resource.load(null);
            return (DomainModel) resource.getContents().get(0);
        } catch (IOException e) {
            throw new MetaModelException("getDomainModelContext Error: maybe, the FilePath does not exist", e);
        }
    }

    /**
     * Adds the content type.
     *
     * @param dme
     *            the dme
     * @param propertyName
     *            the property name
     * @param propertyType
     *            the property type
     */
    public static void addContentType(Entity entity, String propertyName, DatabaseType dbType,
            GrammarFieldType propertyType) {
        if (propertyType.isBinary()) {
            entity.addProperty(createContentTypeProperty(propertyName, dbType));
        }
    }

    /**
     * Creates the content type property.
     *
     * @param propertyName
     *            the property name
     * @return the java property
     */
    public static JavaProperty createContentTypeProperty(String propertyName, DatabaseType dbType) {
        return JavaProperty.builder(GRAMMAR).withName(propertyName + "ContentType").withType("String")
                .withColumnName(toDataBaseNameConvention(dbType, propertyName + "ContentType"))
                .withColumnType("TextBlob").withQualifiedName("java.lang.String").isNullable(true).isPrimaryKey(false)
                .isIndexed(false).isLargeObject(false).hasSizeValidation(false)
                .withTecnologyEquivalentClass(String.class).build();
    }
}
