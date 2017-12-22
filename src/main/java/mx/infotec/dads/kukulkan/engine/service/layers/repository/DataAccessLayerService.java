package mx.infotec.dads.kukulkan.engine.service.layers.repository;

import java.util.Collection;
import java.util.Map;

import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelElement;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

public interface DataAccessLayerService {

    void visitDomainModelElement(ProjectConfiguration pConf, Collection<DomainModelElement> dmElementCollection,
            Map<String, Object> propertiesMap, String dmgName, DomainModelElement dmElement, String basePackage);

}
