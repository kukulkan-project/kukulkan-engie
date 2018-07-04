package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.createDataContextProperties;

import java.util.List;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextProperties;

import mx.infotec.dads.kukulkan.engine.translator.Source;
import mx.infotec.dads.kukulkan.engine.translator.TranslatorService;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.JavaDomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * TranslatorService for Database elements
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DataBaseTranslatorService implements TranslatorService {

    @Override
    public DomainModel translate(ProjectConfiguration pConf, Source from) {
        DomainModel domainModel = new JavaDomainModel();
        from.getSource(DataStore.class).ifPresent(dataStore -> {
            DataContextProperties properties = createDataContextProperties(dataStore);
            List<DomainModelGroup> dmgList = DataBaseMapping.createSingleDataModelGroupList(pConf, properties);
            domainModel.setDomainModelGroup(dmgList);
        });
        return domainModel;
    }
}
