package mx.infotec.dads.kukulkan.engine.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.util.ModelValidationException;

/**
 * Grammar Validator
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultModelValidator implements ModelValidator {

    public static final Pattern ENTITY_NAME_PATTERN = Pattern.compile("^[A-Z][a-z]+([A-Z][a-z]+)*[a-z]$");

    public static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("^[a-z]+([A-Z][a-z]+)*[a-z]$");

    @Override
    public void validateEntityName(Entity entity) {
        if (!isValid(ENTITY_NAME_PATTERN, entity.getName())) {
            throw new ModelValidationException(entity.getName());
        }
    }

    @Override
    public void validatePropertiesName(Entity entity) {
        entity.getProperties().forEach(property -> {
            if (!isValid(PROPERTY_NAME_PATTERN, property.getName())) {
                throw new ModelValidationException(entity.getName(), property.getName());
            }
        });
    }

    @Override
    public void validateAssociationNames(Entity entity) {
        entity.getAssociations().forEach(association -> {
            if (!isValid(PROPERTY_NAME_PATTERN, association.getToTargetPropertyName())) {
                throw new ModelValidationException(entity.getName(), association.getToTargetPropertyName());
            }
            if (!isValid(PROPERTY_NAME_PATTERN, association.getToSourcePropertyName())) {
                throw new ModelValidationException(entity.getName(), association.getToSourcePropertyName());
            }
        });
    }

    private boolean isValid(Pattern pattern, String toTest) {
        if (toTest != null) {
            return pattern.matcher(toTest).find();
        } else {
            return true;
        }
    }
}
