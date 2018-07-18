package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.engine.translator.SourceType.SCHEMA;
import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.PrimitiveFieldContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * SchemaMapping
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SchemaMapping {

    private SchemaMapping() {
        
    }

//    public static JavaProperty createJavaProperty(Entity entity, ColumnType columnType) {
//        return JavaProperty.builder(SCHEMA).withName(entity.getName()).withPropertyType(propertyType)
//                .withColumnName(entity.getTableName()).isNullable(true).isPrimaryKey(false)
//                .isIndexed(false).addType(field.type).build();
//    }

    public static JavaProperty createJavaProperty(PrimitiveFieldContext field, String propertyName,
            GrammarFieldType propertyType, DatabaseType dbType) {
        return JavaProperty.builder().withName(propertyName).withPropertyType(propertyType)
                .withColumnName(toDataBaseNameConvention(dbType, propertyName)).isNullable(true).isPrimaryKey(false)
                .isIndexed(false).build();
    }
}
