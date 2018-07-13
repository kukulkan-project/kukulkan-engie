package mx.infotec.dads.kukulkan.engine.translator;

import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.createDataContextProperties;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.engine.config.InflectorConf;
import mx.infotec.dads.kukulkan.engine.config.XtextDSLConfiguration;
import mx.infotec.dads.kukulkan.engine.service.DefaultModelValidator;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.service.InflectorServiceImpl;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseSource;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorService;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStore;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStoreType;
import mx.infotec.dads.kukulkan.engine.translator.database.DefaultSchemaAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.database.SchemaAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.dsl.FileSource;
import mx.infotec.dads.kukulkan.engine.translator.dsl.XtextGrammarTranslatorService;
import mx.infotec.dads.kukulkan.engine.util.EntityFactory;
import mx.infotec.dads.kukulkan.engine.util.H2FileDatabaseConfiguration;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.nlp.inflector.core.Inflector;
import mx.infotec.dads.nlp.inflector.service.EnglishInflector;
import mx.infotec.dads.nlp.inflector.service.SpanishInflector;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { XtextGrammarTranslatorService.class,
        DataBaseTranslatorService.class, SchemaAnalyzer.class, DefaultSchemaAnalyzer.class, InflectorService.class,
        InflectorServiceImpl.class, Inflector.class, SpanishInflector.class, EnglishInflector.class,
        DefaultModelValidator.class })
@Import({ InflectorConf.class, XtextDSLConfiguration.class })
public class TranslatorServiceTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(TranslatorServiceTest.class);

    @Autowired
    private DataBaseTranslatorService translatorService;

    @Autowired
    private XtextGrammarTranslatorService grammarTranslatorService;

    @BeforeClass
    public static void runOnceBeforeClass() {
        H2FileDatabaseConfiguration.run("relationship-schema.sql");
    }

    @Test
    public void databaseTranslatorService() {
        assertNotNull(translatorService);
        ProjectConfiguration pConf = EntityFactory.createProjectConfiguration(DatabaseType.SQL_MYSQL);
        DataStore dataStore = EntityFactory.createTestDataStore(DataStoreType.SQL);
        Source dataBaseSource = new DataBaseSource(dataStore);
        translatorService.translate(pConf, dataBaseSource);
    }

    @Test
    public void databaseTranslatorServiceTemp() {
        DataStore dataStore = EntityFactory.createTestDataStore(DataStoreType.SQL);
        DataContextProperties properties = createDataContextProperties(dataStore);
        DataContext dataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        dataContext.getSchemaNames().forEach(data -> System.out.println(data));
        Schema schema = dataContext.getDefaultSchema();
        System.out.println("Schema Name :: " + schema.getName());
        System.out.println("**********************************");
        List<Table> tables = schema.getTables();
        for (Table table : tables) {
            System.out.println("Table Name :: " + table.getName());
            table.getColumns().forEach(column -> {
                System.out.println("name[" + column.getName() + "] type[" + column.getType() + "]");
            });
        }
        System.out.println("**********************************");
    }

    @Test
    public void grammarTranslatorServiceWithXtextSemanticAnalyzer() {
        ProjectConfiguration pConf = EntityFactory.createProjectConfiguration(DatabaseType.SQL_MYSQL);
        Source fileSource = new FileSource("/home/roberto/git/kukulkan-engine/src/test/resources/domain-model.3k");
        DomainModel dm = grammarTranslatorService.translate(pConf, fileSource);
        getEntity(dm, "Persona").ifPresent(entity -> {
            assert "id".equals(entity.getDisplayField().getName());
        });
        ;
        assert !dm.getDomainModelGroup().get(0).getEntities().isEmpty();
    }

    private static Optional<Entity> getEntity(DomainModel domainModel, String entityName) {
        for (Entity entity : domainModel.getDomainModelGroup().get(0).getEntities()) {
            if (entityName.equals(entity.getName())) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

}