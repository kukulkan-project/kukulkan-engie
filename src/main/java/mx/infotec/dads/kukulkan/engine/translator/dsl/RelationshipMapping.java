package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.metamodel.MetaModelException;

import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;

/**
 * RelationshipMappings
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class RelationshipMapping {

    private RelationshipMapping() {

    }

    public static List<Entity> mapRelationships(VisitorContext vctx) {
        List<Entity> entities = vctx.getElements();
        vctx.getAssociations().stream().forEach(association -> mapAssociationToEntities(association, entities));
        return entities;
    }

    public static EntityAssociation mapAssociationToEntities(Association association, List<Entity> entities) {
        EntityAssociation ea = new EntityAssociation();
        Entity entitySource = findEntityInList(association.getSource(), entities);
        Entity entityTarget = findEntityInList(association.getTarget(), entities);
        ea.setSource(entitySource);
        ea.setTarget(entityTarget);
        ea.setType(association.getType());
        ea.setSourcePropertyName(association.getSourcePropertyName());
        ea.setTargetPropertyName(association.getTargetPropertyName());
        entitySource.getAssociations().add(ea);
        entityTarget.getAssociations().add(ea);
        return ea;
    }

    public static Entity findEntityInList(String entityName, List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.getName().equals(entityName)) {
                return entity;
            }
        }
        throw new MetaModelException("entity name: [" + entityName
                + "] have not been founded in the Entity List, this name must match some value in the List ");
    }

}
