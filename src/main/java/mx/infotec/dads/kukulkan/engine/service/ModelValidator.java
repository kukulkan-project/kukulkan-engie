package mx.infotec.dads.kukulkan.engine.service;

import java.util.Collection;

import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * Grammar Validator
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface ModelValidator {

    default void validateModel(DomainModel domainModel) {
        validateDomainModelGroup(domainModel.getDomainModelGroup());
    }

    default void validateDomainModelGroup(Collection<DomainModelGroup> dmg) {
        dmg.forEach(group -> {
            if (group.getDomainModelGroup() != null && !group.getDomainModelGroup().isEmpty()) {
                validateDomainModelGroup(group.getDomainModelGroup());
            } else {
                validateEntities(group.getEntities());
            }
        });
    }

    default void validateEntities(Collection<Entity> entities) {
        entities.forEach(entity -> {
            validateEntityName(entity);
            validatePropertiesName(entity);
            validateAssociationNames(entity);
        });
    }

    public void validateEntityName(Entity entity);

    public void validatePropertiesName(Entity entity);

    public void validateAssociationNames(Entity entity);

}
