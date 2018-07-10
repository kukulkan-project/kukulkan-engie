package mx.infotec.dads.kukulkan.engine.translator.database;

import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;

/**
 * SchemaAnalyzers
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@FunctionalInterface
public interface SchemaAnalyzer {

    public DomainModel analyse(SchemaAnalyzerContext context);
}
