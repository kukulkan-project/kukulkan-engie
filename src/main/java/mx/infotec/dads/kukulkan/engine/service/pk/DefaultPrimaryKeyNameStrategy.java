package mx.infotec.dads.kukulkan.engine.service.pk;

import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.model.DefaultPhysicalName;
import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalName;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultPrimaryKeyNameStrategy implements PrimaryKeyNameStrategy {

    @Override
    public PhysicalName getPrimaryKeyPhysicalName(Entity entity) {
        return new DefaultPhysicalName("id", "id");
    }

}
