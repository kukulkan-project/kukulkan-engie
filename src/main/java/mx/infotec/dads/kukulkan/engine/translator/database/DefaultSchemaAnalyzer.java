package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.engine.language.JavaPropertyUtil.createJavaPropertyBuilder;
import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.fieldTypeFrom;
import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.propertyNameFrom;
import static mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil.addContentType;
import static mx.infotec.dads.kukulkan.engine.util.DataBaseMapping.createDefaultPrimaryKey;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.model.AssociationProcessor;
import mx.infotec.dads.kukulkan.engine.model.EntityHolder;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.service.PropertyRankStrategy;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.metamodel.conventions.CodeStandard;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;
import mx.infotec.dads.kukulkan.metamodel.foundation.GrammarFieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Property;

/**
 * DefatulSchemaAnalyzer
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultSchemaAnalyzer extends TemplateSchemaAnalyzer {

    @Autowired
    private InflectorService inflectorService;

    @Autowired
    private PropertyRankStrategy rankStrategy;


    @Autowired
    @Qualifier("defaultPrimaryKeyNameStrategy")
    private PrimaryKeyNameStrategy defaultPrimaryKeyNameStrategy;

    @Autowired
    @Qualifier("composedPrimaryKeyNameStrategy")
    private PrimaryKeyNameStrategy composedKeyNameStrategy;
    

    @Override
    public void processPrimaryKey(SchemaAnalyzerContext context, Entity entity, Column column) {
        PrimaryKeyNameStrategy primaryKeyNameStrategy = resolvePrimaryKeyNameStrategy(context.getProjectConfiguration().getCodeStandard());
        DatabaseType databaseType = context.getProjectConfiguration().getTargetDatabase().getDatabaseType();
        entity.setPrimaryKey(createDefaultPrimaryKey(databaseType, "id", primaryKeyNameStrategy.getPrimaryKeyPhysicalName(entity)));
    }

    @Override
    public void processTimeBasedColumn(SchemaAnalyzerContext context, Entity entity, Column column,
            boolean isForeignKey) {
        processColumnType(context, entity, column, isForeignKey);
    }

    @Override
    public void processNumberColumn(SchemaAnalyzerContext context, Entity entity, Column column, boolean isForeignKey) {
        processColumnType(context, entity, column, isForeignKey);
    }

    @Override
    public void processLiteralColumn(SchemaAnalyzerContext context, Entity entity, Column column,
            boolean isForeignKey) {
        processColumnType(context, entity, column, isForeignKey);
    }

    @Override
    public void processLargeObjectColumn(SchemaAnalyzerContext context, Entity entity, Column column,
            boolean isForeignKey) {
        processColumnType(context, entity, column, isForeignKey);
    }

    @Override
    public void processBooleanColumn(SchemaAnalyzerContext context, Entity entity, Column column,
            boolean isForeignKey) {
        processColumnType(context, entity, column, isForeignKey);
    }

    @Override
    public void processBinaryColumn(SchemaAnalyzerContext context, Entity entity, Column column, boolean isForeignKey) {
        processColumnType(context, entity, column, isForeignKey);
    }

    @Override
    public void processRelationships(SchemaAnalyzerContext context, EntityHolder entityHolder,
            Collection<Relationship> relationships) {
        AssociationProcessor associationProcessor = new AssociationProcessor(inflectorService);
        associationProcessor.processRelationships(relationships, entityHolder);
    }

    public void processColumnType(SchemaAnalyzerContext context, Entity entity, Column column, boolean isForeignKey) {
        if (isForeignKey) {
            return;
        }
        DatabaseType databaseType = context.getProjectConfiguration().getTargetDatabase().getDatabaseType();
        GrammarFieldType fieldType = fieldTypeFrom(column);
        String propertyName = propertyNameFrom(column);
        JavaProperty javaProperty = createJavaPropertyBuilder(propertyName, fieldType, databaseType)
                .isIndexed(column.isIndexed()).isNullable(column.isNullable()).maxSize(column.getColumnSize()).build();
        entity.addProperty(javaProperty);
        addContentType(entity, propertyName, databaseType, fieldType);
        GrammarMapping.addImports(entity.getImports(), javaProperty);
        DataBaseMapping.fillModelMetaData(entity, javaProperty);
    }

    @Override
    public Optional<Property<?>> resolveDisplayField(Collection<Property> properties) {
        return rankStrategy.rank(properties);
    }
    
    private PrimaryKeyNameStrategy resolvePrimaryKeyNameStrategy(CodeStandard cs) {
        if (cs.equals(CodeStandard.DEFAULT)) {
            return defaultPrimaryKeyNameStrategy;
        } else {
            return composedKeyNameStrategy;
        }
    }
}
