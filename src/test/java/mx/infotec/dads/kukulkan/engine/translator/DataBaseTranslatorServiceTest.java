package mx.infotec.dads.kukulkan.engine.translator;

import static mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorUtil.createDataContextProperties;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.engine.config.InflectorConf;
import mx.infotec.dads.kukulkan.engine.config.XtextDSLConfiguration;
import mx.infotec.dads.kukulkan.engine.service.DefaultGeneratorPrintProvider;
import mx.infotec.dads.kukulkan.engine.service.DefaultModelValidator;
import mx.infotec.dads.kukulkan.engine.service.DefaultPhysicalNameConventionService;
import mx.infotec.dads.kukulkan.engine.service.DefaultPropertyRankStrategy;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.service.InflectorServiceImpl;
import mx.infotec.dads.kukulkan.engine.service.PropertyRankStrategy;
import mx.infotec.dads.kukulkan.engine.service.pk.ComposedPrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.engine.service.pk.DefaultPrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.engine.service.references.CustomPhysicalReferenceNameStrategy;
import mx.infotec.dads.kukulkan.engine.service.references.DefaultPhysicalReferenceNameStrategy;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseSource;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorService;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStore;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStoreType;
import mx.infotec.dads.kukulkan.engine.translator.database.DefaultSchemaAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.database.SchemaAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarTranslatorService;
import mx.infotec.dads.kukulkan.engine.util.EntityFactory;
import mx.infotec.dads.kukulkan.engine.util.H2FileDatabaseConfiguration;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.foundation.Property;
import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.nlp.inflector.core.Inflector;
import mx.infotec.dads.nlp.inflector.service.EnglishInflector;
import mx.infotec.dads.nlp.inflector.service.SpanishInflector;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { GrammarTranslatorService.class,
        DataBaseTranslatorService.class, SchemaAnalyzer.class, DefaultSchemaAnalyzer.class, InflectorService.class,
        InflectorServiceImpl.class, Inflector.class, SpanishInflector.class, EnglishInflector.class,
        DefaultModelValidator.class, PropertyRankStrategy.class, DefaultPropertyRankStrategy.class,
        DefaultPrimaryKeyNameStrategy.class, DefaultPhysicalNameConventionService.class,
        DefaultPrimaryKeyNameStrategy.class, ComposedPrimaryKeyNameStrategy.class,
        DefaultPhysicalReferenceNameStrategy.class, CustomPhysicalReferenceNameStrategy.class,
        DefaultGeneratorPrintProvider.class })
@Import({ InflectorConf.class, XtextDSLConfiguration.class })
public class DataBaseTranslatorServiceTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataBaseTranslatorServiceTest.class);

    @Autowired
    private DataBaseTranslatorService translatorService;

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
        DomainModel domainModel = translatorService.translate(pConf, dataBaseSource);
        domainModel.getDomainModelGroup().forEach(group -> {
            group.getEntities().forEach(entity -> {
                System.out.println(entity.getName());
                Collection<Property> properties = entity.getProperties();
                properties.forEach(property -> {
                    System.out.println(property.getName());
                });
                System.out.println("******");
            });
        });
    }

    // @Test
    public void databaseTranslatorServiceTemp() {
        DataStore dataStore = EntityFactory.createMySqlDataStore();
        DataContextProperties properties = createDataContextProperties(dataStore);
        DataContext dataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        dataContext.getSchemaNames().forEach(data -> System.out.println(data));
        Schema schema = dataContext.getDefaultSchema();
        System.out.println("Schema Name :: " + schema.getName());
        System.out.println("**********************************");
        List<Table> tables = schema.getTables();
        for (Table table : tables) {
            System.out.println("**********************************");
            System.out.println("Table Name :: " + table.getName());
            System.out.println("**********************************");
            table.getColumns().forEach(column -> {
                System.out.println("name[" + column.getName() + "] type[" + column.getType() + "]");
            });
            table.getRelationships().forEach(relationship -> {
                System.out.println(relationship.toString());
            });

        }
        System.out.println("**********************************");
    }
}