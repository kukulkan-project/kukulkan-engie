package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Association> associations = vctx.getAssociations();
        associations.parallelStream().map(association -> toEntityAssociation(association, entities))
                .collect(Collectors.toList());
        return entities;
    }

    public static EntityAssociation toEntityAssociation(Association association, List<Entity> entities) {
        EntityAssociation ea = new EntityAssociation();
        ea.setSourcePropertyName(association.getSourcePropertyName());
        ea.setTargetPropertyName(association.getTargetPropertyName());
        return ea;
    }

}
