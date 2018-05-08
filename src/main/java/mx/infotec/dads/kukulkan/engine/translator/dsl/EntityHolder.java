package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.util.HashMap;
import java.util.Map;

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
     * 
     * @param entityName
     * @return
     */
    public Entity getEntity(String entityName) {
        Entity source = entityMap.get(entityName);
        if (source == null) {
            source = Entity.createDomainModelElement();
            source.setName(entityName);
        }
        return source;
    }
}
