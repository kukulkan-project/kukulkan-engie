package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.fieldTypeFrom;
import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.propertyNameFrom;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addContentType;
import static mx.infotec.dads.kukulkan.engine.util.DataBaseMapping.createDefaultPrimaryKey;

import java.util.Collection;

import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Relationship;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.language.JavaPropertyUtil;
import mx.infotec.dads.kukulkan.engine.translator.dsl.EntityHolder;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.GrammarFieldType;

/**
 * DefatulSchemaAnalyzer
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultSchemaAnalyzer extends TemplateSchemaAnalyzer {

    @Override
    public void processPrimaryKey(SchemaAnalyzerContext context, Entity entity, Column column) {
        DatabaseType databaseType = context.getProjectConfiguration().getDatabase().getDatabaseType();
        entity.setPrimaryKey(createDefaultPrimaryKey(databaseType));
    }

    @Override
    public void processTimeBasedColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        processColumnType(context, entity, column);
    }

    @Override
    public void processNumberColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        processColumnType(context, entity, column);
    }

    @Override
    public void processLiteralColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        processColumnType(context, entity, column);
    }

    @Override
    public void processLargeObjectColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        processColumnType(context, entity, column);
    }

    @Override
    public void processBooleanColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        processColumnType(context, entity, column);
    }

    @Override
    public void processBinaryColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        processColumnType(context, entity, column);
    }

    @Override
    public void processRelationships(SchemaAnalyzerContext context, EntityHolder entityHolder,
            Collection<Relationship> relationships) {
    }

    public void processColumnType(SchemaAnalyzerContext context, Entity entity, Column column) {
        DatabaseType databaseType = context.getProjectConfiguration().getDatabase().getDatabaseType();
        GrammarFieldType fieldType = fieldTypeFrom(column);
        String propertyName = propertyNameFrom(column);
        JavaProperty javaProperty = JavaPropertyUtil.createJavaProperty(propertyName, fieldType, databaseType);
        // javaProperty.setConstraint(constraint);
        // setPropertyToShow();
        entity.addProperty(javaProperty);
        addContentType(entity, propertyName, databaseType, fieldType);
        GrammarMapping.addImports(entity.getImports(), javaProperty);
        DataBaseMapping.fillModelMetaData(entity, javaProperty);
    }

}
