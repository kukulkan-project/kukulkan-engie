package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;

/**
 * SchemaMapping
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SchemaMapping {

    private SchemaMapping() {

    }

    public static JavaProperty createJavaProperty(String propertyName, GrammarFieldType propertyType,
            DatabaseType dbType) {
        return JavaProperty.builder().withName(propertyName).withPropertyType(propertyType)
                .withColumnName(toDataBaseNameConvention(dbType, propertyName)).isNullable(true).isPrimaryKey(false)
                .isIndexed(false).build();
    }
}
