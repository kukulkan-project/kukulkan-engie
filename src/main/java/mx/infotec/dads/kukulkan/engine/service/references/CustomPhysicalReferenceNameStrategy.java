package mx.infotec.dads.kukulkan.engine.service.references;

import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalReferenceNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;

/**
 * DefaultPhysicalReferenceNameStrategy used for default Table reference
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class CustomPhysicalReferenceNameStrategy implements PhysicalReferenceNameStrategy {

	@Override
	public String getPhysicalReferenceName(EntityAssociation entityAssociation, boolean toSource) {
		return toSource ? getToSourcePhysicalReferenceName(entityAssociation)
				: getToTargetPhysicalReferenceName(entityAssociation);
	}

	public String getToSourcePhysicalReferenceName(EntityAssociation entityAssociation) {
		return "fk_id_" + entityAssociation.getSource().getTableName();
	}

	public String getToTargetPhysicalReferenceName(EntityAssociation entityAssociation) {
		return "fk_id_" + entityAssociation.getTarget().getTableName();
	}

}
