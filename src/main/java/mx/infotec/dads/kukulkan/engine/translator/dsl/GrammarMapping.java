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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.ResourceSet;

import mx.infotec.dads.kukulkan.dsl.kukulkan.BlobFieldType;
import mx.infotec.dads.kukulkan.dsl.kukulkan.BooleanFieldType;
import mx.infotec.dads.kukulkan.dsl.kukulkan.DateFieldType;
import mx.infotec.dads.kukulkan.dsl.kukulkan.DomainModel;
import mx.infotec.dads.kukulkan.dsl.kukulkan.NumericFieldType;
import mx.infotec.dads.kukulkan.dsl.kukulkan.StringFieldType;
import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.metamodel.foundation.AssociationType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * DataMapping utility class.
 *
 * @author Daniel Cortes Pichardo
 */
public class GrammarMapping {

    /**
     * Instantiates a new grammar mapping.
     */
    private GrammarMapping() {

    }

    /**
     * Create a DataModelGroup Class.
     *
     * @param dmc
     *            the dmc
     * @param visitor
     *            the visitor
     * @return DataModelGroup
     */
    public static DomainModelGroup createDefaultDataModelGroup(DomainModel dm, XtextSemanticAnalyzer kukulkanSwitch) {
        DomainModelGroup dmg = new DomainModelGroup();
        dmg.setName("");
        dmg.setDescription("Default package");
        dmg.setBriefDescription("Default package");
        dmg.setEntities(new ArrayList<>());
        List<Entity> dmeList = new ArrayList<>();
        createDataModelElement(dm, kukulkanSwitch, dmeList);
        dmg.setEntities(dmeList);
        return dmg;
    }

    /**
     * createDataModelElement is used for map the KukulkanGrammar to
     * DataModelElement.
     *
     * @param dmc
     *            the dmc
     * @param visitor
     *            the visitor
     * @param dmeList
     *            the dme list
     */
    private static void createDataModelElement(DomainModel dm, XtextSemanticAnalyzer kukulkanSwitch,
            List<Entity> dmeList) {
        kukulkanSwitch.doSwitch(dm);
        dmeList.addAll(kukulkanSwitch.getVctx().getElements());
    }

    /**
     * Adds the imports.
     *
     * @param imports
     *            the imports
     * @param property
     *            the property
     * @return true, if successful
     */
    public static boolean addImports(Collection<String> imports, JavaProperty property) {
        if (property.isBlob() || property.isLiteral() || property.isBoolean() || property.isClob()
                || (property.isNumber() && !property.isBigDecimal())) {
            return false;
        } else {
            imports.add(property.getQualifiedName());
            return true;
        }
    }

    /**
     * Adds the type.
     *
     * @param javaProperty
     *            the java property
     * @param type
     *            the type
     */
    public static void addType(JavaProperty javaProperty, mx.infotec.dads.kukulkan.dsl.kukulkan.FieldType type) {
        if (type instanceof BooleanFieldType) {
            javaProperty.setBoolean(true);
        } else if (type instanceof DateFieldType) {
            DateFieldType dateFieldType = (DateFieldType) type;
            setKindOfDateType(javaProperty, dateFieldType.getType());
        } else if (type instanceof BlobFieldType) {
            BlobFieldType blobFieldType = (BlobFieldType) type;
            setKindOfBlobType(javaProperty, blobFieldType.getName());
        } else if (type instanceof NumericFieldType) {
            NumericFieldType numericFieldType = (NumericFieldType) type;
            setKindOfNumeric(javaProperty, numericFieldType.getName());
        } else if (type instanceof StringFieldType) {
            javaProperty.setString(true);
        }
    }

    /**
     * Sets the kind of numeric.
     *
     * @param javaProperty
     *            the java property
     * @param type
     *            the type
     */
    private static void setKindOfNumeric(JavaProperty javaProperty, String type) {
        if ("BigDecimal".equals(type)) {
            javaProperty.setBigDecimal(true);
        }
    }

    /**
     * Sets the kind of blob type.
     *
     * @param property
     *            the property
     * @param ctx
     *            the ctx
     */
    private static void setKindOfBlobType(JavaProperty property, String type) {
        if ("Blob".equals(type)) {
            property.setBlob(true);
        } else if ("AnyBlob".equals(type)) {
            property.setBlob(true);
            property.setAnyBlob(true);
        } else if ("ImageBlob".equals(type)) {
            property.setBlob(true);
            property.setImageBlob(true);
        } else if ("TextBlob".equals(type)) {
            property.setClob(true);
        }
    }

    /**
     * Sets the kind of date type.
     *
     * @param property
     *            the property
     * @param type
     *            the type
     */
    private static void setKindOfDateType(JavaProperty property, String dateType) {
        if ("ZonedDateTime".equals(dateType)) {
            property.setZoneDateTime(true);
        } else if ("LocalDate".equals(dateType)) {
            property.setLocalDate(true);
        } else if ("Instant".equals(dateType)) {
            property.setInstant(true);
        }
    }

    /**
     * createSingleDataModelGroupList.
     *
     * @param visitor
     *            the visitor
     * @param file
     *            the file
     * @return the list
     */
    public static List<DomainModelGroup> createSingleDataModelGroupList(XtextSemanticAnalyzer kukulkanSwitch, File file,
            ResourceSet resourceSet) {
        String program = file.getAbsolutePath();
        DomainModel ast = GrammarUtil.getDomainModelAST(program, resourceSet);
        List<DomainModelGroup> dataModelGroupList = new ArrayList<>();
        dataModelGroupList.add(createDefaultDataModelGroup(ast, kukulkanSwitch));
        return dataModelGroupList;
    }

    /**
     * Gets the property type.
     *
     * @param type
     *            the type
     * @return the property type
     */
    public static Optional<GrammarFieldType> getPropertyType(String type) {
        return Optional.of(GrammarFieldTypeMapping.getPropertyType(type));
    }

    public static AssociationType resolveAssociationType(Entity sourceEntity, String type) {
        Objects.requireNonNull(type);
        if ("OneToOne".equals(type)) {
            sourceEntity.setHasOneToOne(true);
            return AssociationType.ONE_TO_ONE;
        } else if ("OneToMany".equals(type)) {
            sourceEntity.setHasOneToMany(true);
            return AssociationType.ONE_TO_MANY;
        } else if ("ManyToOne".equals(type)) {
            sourceEntity.setHasManyToOne(true);
            return AssociationType.MANY_TO_ONE;
        } else if ("ManyToMany".equals(type)) {
            sourceEntity.setHasManyToMany(true);
            return AssociationType.MANY_TO_MANY;
        }
        throw new MetaModelException(
                "Expected neither OneToOne, OneToMany, ManyToOne or ManyToMany but found ->" + type);
    }
}
