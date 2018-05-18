package mx.infotec.dads.kukulkan.engine.service;

import java.util.Collection;
import java.util.List;

import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;
import mx.infotec.dads.kukulkan.metamodel.foundation.Property;

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
            if (!group.getDomainModelGroup().isEmpty()) {
                validateDomainModelGroup(group.getDomainModelGroup());
            } else {
                validateEntities(group.getEntities());
            }
        });
    }

    default void validateEntities(Collection<Entity> entities) {
        entities.forEach(entity -> {
            validateEntityName(entity.getName());
            validatePropertiesName(entity.getProperties());
            validateAssociationNames(entity.getAssociations());
        });
    }

    public void validateEntityName(String entityName);

    public void validatePropertiesName(Collection<Property> properties);

    public void validateAssociationNames(List<EntityAssociation> associations);

}
