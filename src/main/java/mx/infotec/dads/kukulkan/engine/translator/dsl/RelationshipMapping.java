package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.util.List;

import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

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
        List<Association> associations = vctx.getAssociations();
        for (Entity entity : entities) {
            
        }
        return entities;
    }
    
}
