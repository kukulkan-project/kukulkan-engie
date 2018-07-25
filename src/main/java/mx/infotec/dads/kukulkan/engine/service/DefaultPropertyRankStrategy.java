package mx.infotec.dads.kukulkan.engine.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.FieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Property;

/**
 * DefaultPropertyRankStrategy
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultPropertyRankStrategy implements PropertyRankStrategy {

    @Override
    @SuppressWarnings("rawtypes")
    public Optional<Property<?>> rank(Collection<Property> properties) {
        Property<?> targetProperty = null;
        int max = -1;
        for (Property property : properties) {
            Constraint constraint = property.getConstraint();
            if (FieldType.STRING.text().equals(property.getType())) {
                if (constraint.isUnique()) {
                    max = 1;
                    targetProperty = property;
                } else {
                    int maxValue = getMaxValue(constraint.getMax());
                    if (((maxValue < max) && (maxValue > -1)) || (max == -1)) {
                        max = maxValue;
                        targetProperty = property;
                    }
                }
            }
        }
        if (targetProperty != null) {
            return Optional.of(targetProperty);
        } else {
            return Optional.empty();
        }
    }

    private static int getMaxValue(String maxValue) {
        try {
            return Integer.valueOf(maxValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
