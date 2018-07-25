package mx.infotec.dads.kukulkan.engine.service;

import java.util.Collection;

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
    public Property rank(Collection<Property> properties) {
//        Map<String, Property> rank = new HashMap<>();
        for (Property property : properties) {
            if (FieldType.STRING.text().equals(property.getType())) {
                return property;
            }
        }
        return null;
    }
}
