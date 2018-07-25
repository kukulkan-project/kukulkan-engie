package mx.infotec.dads.kukulkan.engine.service;

import java.util.Collection;
import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.foundation.Property;

/**
 * PropertyRankStrategy
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface PropertyRankStrategy {

    Optional<Property<?>> rank(Collection<Property> properties);
}
