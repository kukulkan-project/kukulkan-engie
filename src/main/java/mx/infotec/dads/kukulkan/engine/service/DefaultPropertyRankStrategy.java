package mx.infotec.dads.kukulkan.engine.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
        for (Property property : properties) {
            if (FieldType.STRING.text().equals(property.getType())) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }
}
