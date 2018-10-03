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
public class DefaultPhysicalReferenceNameStrategy implements PhysicalReferenceNameStrategy {

	@Override
	public String getPhysicalReferenceName(EntityAssociation entityAssociation, boolean toSource) {
		return toSource ? getToSourcePhysicalReferenceName(entityAssociation)
				: getToTargetPhysicalReferenceName(entityAssociation);
	}

	public String getToSourcePhysicalReferenceName(EntityAssociation entityAssociation) {
		return entityAssociation.getToSourcePropertyNameUnderscorePlural() != null
				? entityAssociation.getToSourcePropertyNameUnderscorePlural() + "_id"
				: null;
	}

	public String getToTargetPhysicalReferenceName(EntityAssociation entityAssociation) {
		return entityAssociation.getToSourcePropertyNameUnderscorePlural() != null
				? entityAssociation.getToTargetPropertyNameUnderscorePlural() + "_id"
				: null;
	}
}
