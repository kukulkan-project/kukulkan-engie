package mx.infotec.dads.kukulkan.engine.service.pk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.model.DefaultPrimaryKeyName;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalName;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class ComposedPrimaryKeyNameStrategy implements PrimaryKeyNameStrategy {

    @Autowired
    private InflectorService inflectorService;

    @Override
    public PhysicalName getPrimaryKeyPhysicalName(Entity entity) {
        return new DefaultPrimaryKeyName("id" + entity.getName(), "id" + inflectorService.pluralize(entity.getName()));
    }

}
