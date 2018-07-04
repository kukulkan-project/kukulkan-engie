package mx.infotec.dads.kukulkan.engine.translator;

import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.createDataContextProperties;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextProperties;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseSource;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorService;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStore;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStoreType;
import mx.infotec.dads.kukulkan.engine.util.EntityFactory;
import mx.infotec.dads.kukulkan.engine.util.H2FileDatabaseConfiguration;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ContextConfiguration(classes = { TranslatorService.class, DataBaseTranslatorService.class })
public class TranslatorServiceTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(TranslatorServiceTest.class);

    @Autowired
    private DataBaseTranslatorService translatorService;

    @BeforeClass
    public static void runOnceBeforeClass() {
        H2FileDatabaseConfiguration.run();
    }

    public void databaseTranslatorService() {
        assertNotNull(translatorService);
        ProjectConfiguration pConf = EntityFactory.createProjectConfiguration(DatabaseType.SQL_MYSQL);
        DataStore dataStore = EntityFactory.createTestDataStore(DataStoreType.SQL);
        Source dataBaseSource = new DataBaseSource(dataStore);
        DomainModel model = translatorService.translate(pConf, dataBaseSource);
    }

//    @Test
    public void databaseTranslatorServiceTemp() {
        DataStore dataStore = EntityFactory.createTestDataStore(DataStoreType.SQL);
        DataContextProperties properties = createDataContextProperties(dataStore);
        DataContext dataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        dataContext.getSchemaNames().forEach(data -> System.out.println(data));
        Schema schema = dataContext.getDefaultSchema();
        List<Table> tables = schema.getTables();
        for (Table table : tables) {
            
        }
    }
}