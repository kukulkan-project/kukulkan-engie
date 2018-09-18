package mx.infotec.dads.kukulkan.engine.service.pk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;

import mx.infotec.dads.kukulkan.engine.model.DefaultPhysicalName;
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
        String entityPlural = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
                inflectorService.pluralize(entity.getName()));
        return new DefaultPhysicalName("id" + entity.getName(), "id" + entityPlural);
    }

}
