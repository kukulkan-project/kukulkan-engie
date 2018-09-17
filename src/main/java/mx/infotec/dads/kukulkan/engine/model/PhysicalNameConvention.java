package mx.infotec.dads.kukulkan.engine.model;

import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalReferenceNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;

/**
 * PhysicalNameConvention
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class PhysicalNameConvention {

    private PhysicalReferenceNameStrategy physicalReferenceNameStrategy;

    private PrimaryKeyNameStrategy primaryKeyNameStrategy;

    public PhysicalNameConvention(PrimaryKeyNameStrategy pks, PhysicalReferenceNameStrategy prns) {
        this.primaryKeyNameStrategy = pks;
        this.physicalReferenceNameStrategy = prns;
    }

    public PhysicalReferenceNameStrategy getPhysicalReferenceNameStrategy() {
        return physicalReferenceNameStrategy;
    }

    public PrimaryKeyNameStrategy getPrimaryKeyNameStrategy() {
        return primaryKeyNameStrategy;
    }
}
