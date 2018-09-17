package mx.infotec.dads.kukulkan.engine.translator.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.metamodel.factory.DataContextProperties;

import mx.infotec.dads.kukulkan.engine.model.PhysicalNameConvention;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * Schema Analyzer Context, it is used like a context container
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SchemaAnalyzerContext {

    private DataContextProperties dataContextProperties;
    private ProjectConfiguration projectConfiguration;
    private PhysicalNameConvention physicalNameConvention;

    private List<Entity> elements = new ArrayList<>();

    public SchemaAnalyzerContext(DataContextProperties dataContextProperties,
            ProjectConfiguration projectConfiguration, PhysicalNameConvention physicalNameConvention) {
        this.dataContextProperties = dataContextProperties;
        this.projectConfiguration = projectConfiguration;
        this.physicalNameConvention = physicalNameConvention;
    }

    public DataContextProperties getDataContextProperties() {
        return dataContextProperties;
    }

    public ProjectConfiguration getProjectConfiguration() {
        return projectConfiguration;
    }

    public List<Entity> getElements() {
        return elements;
    }

    public void addEntity(Entity entity) {
        this.elements.add(entity);
    }

    public PhysicalNameConvention getPhysicalNameConvention() {
        return physicalNameConvention;
    }
}
