package mx.infotec.dads.kukulkan.engine.service;

import mx.infotec.dads.kukulkan.engine.model.PhysicalNameConvention;
import mx.infotec.dads.kukulkan.metamodel.conventions.CodeStandard;

/**
 * PhysicalNameConventionService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface PhysicalNameConventionService {

    PhysicalNameConvention getPhysicalNameConvention(CodeStandard codeStandard);
}
