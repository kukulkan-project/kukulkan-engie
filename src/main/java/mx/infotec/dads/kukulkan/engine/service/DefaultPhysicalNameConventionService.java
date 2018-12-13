package mx.infotec.dads.kukulkan.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.model.PhysicalNameConvention;
import mx.infotec.dads.kukulkan.metamodel.conventions.CodeStandard;
import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalReferenceNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;

/**
 * PhysicalNameConventionService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service("defaultPhysicalNameConventionService")
public class DefaultPhysicalNameConventionService implements PhysicalNameConventionService {

    @Autowired
    @Qualifier("defaultPrimaryKeyNameStrategy")
    private PrimaryKeyNameStrategy defaultPrimaryKeyNameStrategy;

    @Autowired
    @Qualifier("composedPrimaryKeyNameStrategy")
    private PrimaryKeyNameStrategy composedKeyNameStrategy;

    @Autowired
    @Qualifier("defaultPhysicalReferenceNameStrategy")
    private PhysicalReferenceNameStrategy defaultPhysicalReferenceNameStrategy;

    @Autowired
    @Qualifier("customPhysicalReferenceNameStrategy")
    private PhysicalReferenceNameStrategy customPhysicalReferenceNameStrategy;

    @Override
    public PhysicalNameConvention getPhysicalNameConvention(CodeStandard codeStandard) {
        if (codeStandard.equals(CodeStandard.DEFAULT)) {
            return new PhysicalNameConvention(defaultPrimaryKeyNameStrategy, defaultPhysicalReferenceNameStrategy);
        } else {
            return new PhysicalNameConvention(composedKeyNameStrategy, customPhysicalReferenceNameStrategy);
        }
    }
}
