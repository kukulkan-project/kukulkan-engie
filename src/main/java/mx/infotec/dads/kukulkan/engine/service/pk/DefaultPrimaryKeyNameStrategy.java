package mx.infotec.dads.kukulkan.engine.service.pk;

import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;

/**
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultPrimaryKeyNameStrategy implements PrimaryKeyNameStrategy {
    /**
     * The Constant ID_DEFAULT_NAME.
     */
    public static final String ID_DEFAULT_NAME = "id";

    @Override
    public String getName(Entity entity) {
        return ID_DEFAULT_NAME;
    }

}
