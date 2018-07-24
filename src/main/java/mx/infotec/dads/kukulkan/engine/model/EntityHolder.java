package mx.infotec.dads.kukulkan.engine.model;

import static mx.infotec.dads.kukulkan.metamodel.util.NameConventionFormatter.toDataBaseNameConvention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.metamodel.MetaModelException;

import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * EntityHolder
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class EntityHolder {

    private Map<String, Entity> entityMap = new HashMap<>();

    /**
     * Get a entity from entityMap. This method return a default entity if not
     * present
     * 
     * @param entityName
     * @return
     */
    public Entity getEntity(String entityName) {
        Entity source = entityMap.get(entityName);
        if (source == null) {
            source = Entity.createDomainModelElement();
            source.setName(entityName);
            entityMap.put(source.getName(), source);
        }
        return source;
    }

    /**
     * Get a entity from entityMap. This method return a default entity if not
     * present
     * 
     * @param entityName
     * @return
     */
    public Optional<Entity> findEntity(String entityName) {
        Entity source = entityMap.get(entityName);
        if (source == null) {
            return Optional.empty();
        }
        return Optional.of(source);
    }

    /**
     * Update a entityMap with the new Entity, if the entity is note present, it
     * throws a MetamodelException
     * 
     * @param entity
     */
    public void update(Entity entity) {
        Entity source = entityMap.get(entity.getName());
        if (source == null) {
            throw new MetaModelException("The Entity is not registered : " + entity.getName());
        }
        entityMap.put(source.getName(), entity);
    }

    public List<Entity> getEntitiesAsList() {
        return new ArrayList<>(entityMap.values());
    }
}
