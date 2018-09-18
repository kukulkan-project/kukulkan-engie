package mx.infotec.dads.kukulkan.engine.model;

import static mx.infotec.dads.kukulkan.metamodel.util.SchemaPropertiesParser.databaseNameToFieldName;
import static mx.infotec.dads.kukulkan.metamodel.util.SchemaPropertiesParser.parseToClassName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Relationship;

import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.metamodel.foundation.AssociationType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * Association Processor
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class AssociationProcessor {

    private HashMap<String, EntityAssociation> map = new HashMap<>();

    private InflectorService inflectorService;

    private PhysicalNameConvention nameConvention;

    public AssociationProcessor(InflectorService inflectorService, PhysicalNameConvention physicalNameConvention) {
        this.inflectorService = inflectorService;
        this.nameConvention = physicalNameConvention;
    }

    public EntityAssociation add(Relationship relationship, EntityHolder entityHolder) {
        String key = createKeyMap(relationship);
        EntityAssociation entityAssociation = map.get(key);
        if (entityAssociation == null) {
            String targetEntityName = parseToClassName(relationship.getPrimaryTable().getName());
            String sourceEntityName = parseToClassName(relationship.getForeignTable().getName());
            Entity targetEntity = findEntity(entityHolder, targetEntityName);
            Entity sourceEntity = findEntity(entityHolder, sourceEntityName);
            String foreignColumn = getSingleColumn(relationship.getForeignColumns());
            entityAssociation = createEntityAssociation(targetEntity, sourceEntity, foreignColumn);
            entityAssociation.setType(AssociationType.MANY_TO_ONE);
            sourceEntity.addAssociation(entityAssociation);
        }
        map.put(key, entityAssociation);
        return entityAssociation;
    }

    public EntityAssociation createEntityAssociation(Entity targetEntity, Entity sourceEntity, String foreignColumn) {
        EntityAssociation entityAssociation;
        entityAssociation = EntityAssociation.createEntityAssociation().bidirectional(false).ownerside(false)
                .target(targetEntity).toTargetPropertyName(databaseNameToFieldName(foreignColumn))
                .toTargetPropertyNamePlural(inflectorService.pluralize(databaseNameToFieldName(foreignColumn)))
                .toTargetPropertyNameUnderscore(foreignColumn.toLowerCase())
                .toTargetPropertyNameUnderscorePlural(inflectorService.pluralize(foreignColumn.toLowerCase()))
                .source(sourceEntity).build();
        // For references
        // Si la relación es OneToMany unidireccional entonces se debería usar
        // getSource().getReferencePhysicalName en cualquier otro caso usar
        // getToSourcePropertyNameUnderscorePlural
        entityAssociation.setToSourceReferencePhysicalName(nameConvention.getPhysicalReferenceNameStrategy()
                .getPhysicalReferenceName(entityAssociation.getToSourcePropertyNameUnderscorePlural()));
        entityAssociation.setToTargetReferencePhysicalName(nameConvention.getPhysicalReferenceNameStrategy()
                .getPhysicalReferenceName(entityAssociation.getToTargetPropertyNameUnderscorePlural()));
        return entityAssociation;
    }

    public Entity findEntity(EntityHolder entityHolder, String targetEntityName) {
        return entityHolder.findEntity(targetEntityName)
                .orElseThrow(() -> new RuntimeException("not found : " + targetEntityName));
    }

    public void processRelationships(Collection<Relationship> relationships, EntityHolder entityHolder) {
        relationships.forEach(relationship -> this.add(relationship, entityHolder));
    }

    public List<EntityAssociation> getAssociationsAsList() {
        return new ArrayList<>(map.values());
    }

    private static String getSingleColumn(List<Column> foreignColumns) {
        if (foreignColumns.size() > 1) {
            throw new MetaModelException("No support for Multiple foreignKeys Columns");
        } else {
            return foreignColumns.get(0).getName();
        }
    }

    private static String createKeyMap(Relationship relationship) {
        // by default all foreign tables are sourceTable and primary table are
        // targetEntity
        String targetEntityName = relationship.getPrimaryTable().getName().toUpperCase();
        String targetColumnKey = convertToKeyValue(relationship.getPrimaryColumns());
        String sourceEntityName = relationship.getForeignTable().getName().toUpperCase();
        String sourceColumnKey = convertToKeyValue(relationship.getForeignColumns());
        return targetEntityName + targetColumnKey + sourceEntityName + sourceColumnKey;
    }

    private static String convertToKeyValue(List<Column> columns) {
        StringBuilder sb = new StringBuilder();
        for (Column column : columns) {
            sb.append(column.getName());
        }
        return sb.toString().toUpperCase();
    }

}
