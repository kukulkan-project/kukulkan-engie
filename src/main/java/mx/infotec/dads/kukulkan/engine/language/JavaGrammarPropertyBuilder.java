package mx.infotec.dads.kukulkan.engine.language;

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeMapping;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.FieldTypeContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;

/**
 * The Class JavaGrammarPropertyBuilder.
 */
public class JavaGrammarPropertyBuilder implements PropertyBuilder<JavaProperty> {

    /**
     * The java property.
     */
    private JavaProperty javaProperty;

    /**
     * Instantiates a new java property builder.
     */
    public JavaGrammarPropertyBuilder() {
        this.javaProperty = new JavaProperty();
        this.javaProperty.setConstraint(new Constraint());
    }

    /**
     * With java equivalent class.
     *
     * @param javaEquivalentClass
     *            the java equivalent class
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withTecnologyEquivalentClass(Class<?> javaEquivalentClass) {
        this.javaProperty.setJavaEquivalentClass(javaEquivalentClass);
        return this;
    }

    /**
     * Checks if is sizeValidation.
     *
     * @param sizeValidation
     *            the sizeValidation
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder hasSizeValidation(boolean sizeValidation) {
        javaProperty.setSizeValidation(sizeValidation);
        return this;
    }

    /**
     * With name.
     *
     * @param propertyName
     *            the property name
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withName(String propertyName) {
        this.javaProperty.setName(propertyName);
        return this;
    }

    /**
     * With type.
     *
     * @param propertyType
     *            the property type
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withType(String propertyType) {
        this.javaProperty.setType(propertyType);
        return this;
    }

    /**
     * With propertyType.
     *
     * @param propertyType
     *            the property type
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withPropertyType(GrammarFieldType propertyType) {
        GrammarFieldTypeMapping.configurateGrammarFieldType(propertyType, this.javaProperty);
        this.isLargeObject(propertyType.isLargeObject());
        this.withType(propertyType.getJavaName());
        this.withColumnType(propertyType.getFieldType().text());
        this.withQualifiedName(propertyType.getJavaQualifiedName());
        this.withTecnologyEquivalentClass(propertyType.getJavaEquivalentClass());
        return this;
    }

    /**
     * With qualified name.
     *
     * @param qualifiedName
     *            the qualified name
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withQualifiedName(String qualifiedName) {
        this.javaProperty.setQualifiedName(qualifiedName);
        return this;
    }

    /**
     * With column name.
     *
     * @param columnName
     *            the column name
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withColumnName(String columnName) {
        this.javaProperty.setColumnName(columnName);
        return this;
    }

    /**
     * With column type.
     *
     * @param columnType
     *            the column type
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder withColumnType(String columnType) {
        this.javaProperty.setColumnType(columnType);
        return this;
    }

    /**
     * Checks if is nullable.
     *
     * @param nullable
     *            the nullable
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isNullable(boolean nullable) {
        this.javaProperty.getConstraint().setNullable(nullable);
        return this;
    }

    /**
     * Checks if is primary key.
     *
     * @param isPrimaryKey
     *            the is primary key
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isPrimaryKey(boolean isPrimaryKey) {
        this.javaProperty.getConstraint().setPrimaryKey(isPrimaryKey);
        return this;
    }

    /**
     * Checks if is indexed.
     *
     * @param indexed
     *            the indexed
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isIndexed(boolean indexed) {
        this.javaProperty.getConstraint().setIndexed(indexed);
        return this;
    }

    /**
     * Adds the type.
     *
     * @param type
     *            the type
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder addType(ColumnType type) {
        DataBaseMapping.addType(javaProperty, type);
        return this;
    }

    /**
     * Adds the type.
     *
     * @param type
     *            the type
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder addType(FieldTypeContext type) {
        GrammarMapping.addType(javaProperty, type);
        return this;
    }

    /**
     * Checks if is big decimal.
     *
     * @param bigDecimal
     *            the big decimal
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isBigDecimal(boolean bigDecimal) {
        this.javaProperty.setBigDecimal(bigDecimal);
        return this;
    }

    /**
     * Checks if is local date.
     *
     * @param localDate
     *            the local date
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isLocalDate(boolean localDate) {
        this.javaProperty.setLocalDate(localDate);
        return this;
    }

    /**
     * Checks if is zone date time.
     *
     * @param zoneDateTime
     *            the zone date time
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isZoneDateTime(boolean zoneDateTime) {
        this.javaProperty.setZoneDateTime(zoneDateTime);
        return this;
    }

    /**
     * Checks if is instance.
     *
     * @param instant
     *            the instant
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isInstance(boolean instant) {
        this.javaProperty.setInstant(instant);
        return this;
    }

    /**
     * Checks if is large object.
     *
     * @param largeObject
     *            the large object
     * @return the java property builder
     */
    public JavaGrammarPropertyBuilder isLargeObject(boolean largeObject) {
        this.javaProperty.setLargeObject(largeObject);
        return this;
    }

    /**
     * Builds the.
     *
     * @return the java property
     */
    public JavaProperty build() {
        return this.javaProperty;
    }

}
