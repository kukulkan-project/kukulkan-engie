package mx.infotec.dads.kukulkan.engine.service.references;

import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalReferenceNameStrategy;

/**
 * DefaultPhysicalReferenceNameStrategy used for default Table reference
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DefaultPhysicalReferenceNameStrategy implements PhysicalReferenceNameStrategy {

    @Override
    public String getPhysicalReferenceName(String snakeConventionName) {
        return snakeConventionName + "_id";
    }

}
