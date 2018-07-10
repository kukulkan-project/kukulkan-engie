package mx.infotec.dads.kukulkan.engine.translator.database;

import java.util.Collection;

import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Relationship;
import org.apache.metamodel.schema.Table;
import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.translator.dsl.EntityHolder;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * DefatulSchemaAnalyzer
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DefaultSchemaAnalyzer extends TemplateSchemaAnalyzer {

    @Autowired
    private InflectorService inflectorService;

    @Override
    public void processPrimaryKey(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processTable(SchemaAnalyzerContext context, Entity entity, Table table) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processRelationships(SchemaAnalyzerContext context, EntityHolder entityHolder,
            Collection<Relationship> relationships) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processTimeBasedColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processNumberColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processLiteralColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processLargeObjectColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processBooleanColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processBinaryColumn(SchemaAnalyzerContext context, Entity entity, Column column) {
        // TODO Auto-generated method stub
        
    }

}
