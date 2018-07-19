package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.createDataContextProperties;

import java.util.Optional;

import org.apache.metamodel.factory.DataContextProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.translator.TranslatorService;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * TranslatorService for Database elements
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class DataBaseTranslatorService implements TranslatorService {

    @Autowired
    private SchemaAnalyzer analyzer;

    @Override
    public DomainModel translate(ProjectConfiguration pConf, Source from) {
        Optional<DataStore> dataStoreOptional = from.getSource(DataStore.class);
        if (dataStoreOptional.isPresent()) {
            DataContextProperties properties = createDataContextProperties(dataStoreOptional.get());
            return analyzer.analyse(new SchemaAnalyzerContext(properties, pConf));
        } else {
            throw new MetaModelException("<DataStore> can not be null");
        }

    }
}
