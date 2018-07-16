package mx.infotec.dads.kukulkan.engine.language;

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeMapping;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;

/**
 * BaseJavaPropertyBuilder
 * 
 * @author Daniel Cortes Pichardo
 * @param <T>
 *
 */
public abstract class BaseJavaPropertyBuilder implements PropertyBuilder<JavaProperty> {

    /**
     * The java property.
     */
    private JavaProperty javaProperty;

    public BaseJavaPropertyBuilder() {
        this.javaProperty = new JavaProperty();
        this.javaProperty.setConstraint(new Constraint());
    }

    protected JavaProperty getJavaProperty() {
        return javaProperty;
    }

    @Override
    public PropertyBuilder<JavaProperty> addType(ColumnType type) {
        DataBaseMapping.addType(getJavaProperty(), type);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withTecnologyEquivalentClass(Class<?> technologyEquivalentClass) {
        this.javaProperty.setJavaEquivalentClass(technologyEquivalentClass);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> hasSizeValidation(boolean sizeValidation) {
        javaProperty.setSizeValidation(sizeValidation);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withName(String propertyName) {
        this.javaProperty.setName(propertyName);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withType(String propertyType) {
        this.javaProperty.setType(propertyType);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withQualifiedName(String qualifiedName) {
        this.javaProperty.setQualifiedName(qualifiedName);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withColumnName(String columnName) {
        this.javaProperty.setColumnName(columnName);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withColumnType(String columnType) {
        this.javaProperty.setColumnType(columnType);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isNullable(boolean nullable) {
        this.javaProperty.getConstraint().setNullable(nullable);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isPrimaryKey(boolean isPrimaryKey) {
        this.javaProperty.getConstraint().setPrimaryKey(isPrimaryKey);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isIndexed(boolean indexed) {
        this.javaProperty.getConstraint().setIndexed(indexed);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isBigDecimal(boolean bigDecimal) {
        this.javaProperty.setBigDecimal(bigDecimal);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isLocalDate(boolean localDate) {
        this.javaProperty.setLocalDate(localDate);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isZoneDateTime(boolean zoneDateTime) {
        this.javaProperty.setZoneDateTime(zoneDateTime);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isInstance(boolean instant) {
        this.javaProperty.setInstant(instant);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> isLargeObject(boolean largeObject) {
        this.javaProperty.setLargeObject(largeObject);
        return this;
    }

    @Override
    public PropertyBuilder<JavaProperty> withPropertyType(GrammarFieldType propertyType) {
        GrammarFieldTypeMapping.configurateGrammarFieldType(propertyType, getJavaProperty());
        this.isLargeObject(propertyType.isLargeObject());
        this.withType(propertyType.getJavaName());
        this.withColumnType(propertyType.getFieldType().text());
        this.withQualifiedName(propertyType.getJavaQualifiedName());
        this.withTecnologyEquivalentClass(propertyType.getJavaEquivalentClass());
        return this;
    }

    @Override
    public JavaProperty build() {
        return this.javaProperty;
    }
}
