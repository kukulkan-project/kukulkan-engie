package mx.infotec.dads.kukulkan.engine.translator.database;

import org.apache.metamodel.DataContext;

import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * SchemaAnalyzers
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@FunctionalInterface
public interface SchemaAnalyzer {

    public DomainModel process(DataContext context, ProjectConfiguration configuration);
}
