package mx.infotec.dads.kukulkan.engine.language;

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.FieldTypeContext;

/**
 * Property Builder
 * 
 * @author Daniel Cortes Pichardo
 */
public interface PropertyBuilder<T> {

    public PropertyBuilder<T> withTecnologyEquivalentClass(Class<?> technologyEquivalentClass);

    public PropertyBuilder<T> hasSizeValidation(boolean sizeValidation);

    public PropertyBuilder<T> withName(String propertyName);

    public PropertyBuilder<T> withType(String propertyType);

    public PropertyBuilder<T> withPropertyType(GrammarFieldType propertyType);

    public PropertyBuilder<T> withQualifiedName(String qualifiedName);

    public PropertyBuilder<T> withColumnName(String columnName);

    public PropertyBuilder<T> withColumnType(String columnType);

    public PropertyBuilder<T> isNullable(boolean nullable);

    public PropertyBuilder<T> isPrimaryKey(boolean isPrimaryKey);

    public PropertyBuilder<T> isIndexed(boolean indexed);

    public PropertyBuilder<T> addType(ColumnType type);

    public PropertyBuilder<T> addType(FieldTypeContext type);

    public PropertyBuilder<T> isBigDecimal(boolean bigDecimal);

    public PropertyBuilder<T> isLocalDate(boolean localDate);

    public PropertyBuilder<T> isZoneDateTime(boolean zoneDateTime);

    public PropertyBuilder<T> isInstance(boolean instant);

    public PropertyBuilder<T> isLargeObject(boolean largeObject);

    public T build();

}
