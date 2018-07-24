package mx.infotec.dads.kukulkan.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;

/**
 * Association Holder
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class EntityAssociationHolder {

    private Map<String, EntityAssociation> associationMap = new HashMap<>();

    /**
     * Get a entity from associationMap. This method return a default entity if
     * not present
     * 
     * @param entityName
     * @return
     */
    public EntityAssociation getAssociation(String associationName) {
        EntityAssociation source = associationMap.get(associationName);
        if (source == null) {
            // associationMap.put(source.getName(), source);
        }
        return source;
    }

    /**
     * Get a entity from associationMap. This method return a default entity if
     * not present
     * 
     * @param entityName
     * @return
     */
    public Optional<EntityAssociation> findAssociation(String entityName) {
        EntityAssociation source = associationMap.get(entityName);
        if (source == null) {
            return Optional.empty();
        }
        return Optional.of(source);
    }

    public List<EntityAssociation> getEntitiesAsList() {
        return new ArrayList<>(associationMap.values());
    }
}
