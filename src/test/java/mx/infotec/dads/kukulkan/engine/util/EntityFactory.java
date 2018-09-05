package mx.infotec.dads.kukulkan.engine.util;

import java.time.LocalDateTime;

import mx.infotec.dads.kukulkan.engine.translator.database.DataStore;
import mx.infotec.dads.kukulkan.engine.translator.database.DataStoreType;
import mx.infotec.dads.kukulkan.metamodel.foundation.Database;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.foundation.TableTypes;

public class EntityFactory {

    /*
    public static List<DomainModelGroup> createSingleTestDataModelGroupList(GrammarSemanticAnalyzer visitor) {
        String program = "src/test/resources/grammar/single-entity." + "3k";
        DomainModelContext tree = GrammarUtil.getDomainModelContext(program);
        List<DomainModelGroup> dataModelGroupList = new ArrayList<>();
        dataModelGroupList.add(GrammarMapping.createDefaultDataModelGroup(tree, visitor));
        return dataModelGroupList;
    }

    public static List<DomainModelGroup> createRelationshipTestDataModelGroupList(GrammarSemanticAnalyzer visitor) {
        String program = "src/test/resources/grammar/relationship-entity." + "3k";
        DomainModelContext tree = GrammarUtil.getDomainModelContext(program);
        List<DomainModelGroup> dataModelGroupList = new ArrayList<>();
        dataModelGroupList.add(GrammarMapping.createDefaultDataModelGroup(tree, visitor));
        return dataModelGroupList;
    }
*/
    public static ProjectConfiguration createProjectConfiguration(DatabaseType type) {
        ProjectConfiguration pConf = new ProjectConfiguration();
        pConf.setId("kukulkan");
        pConf.setVersion("1.0.0");
        pConf.setPackaging("mx.infotec.dads.archetype");
        pConf.setYear("2018");
        pConf.setAuthor("KUKULKAN");
        // pConf.setOutputDir(TemporalDirectoryUtil.getTemporalPath());
        pConf.setTargetDatabase(new Database(type));
        pConf.setTimestamp(LocalDateTime.of(2018, 03, 03, 18, 52, 22));
        pConf.addLayers("angular-js", "spring-rest", "spring-service", "spring-repository", "domain-core");
        if (DatabaseType.SQL_MYSQL == type) {
            pConf.addLayer("liquibase");
        }
        return pConf;
    }
    

    public static DataStore createTestDataStore(DataStoreType dst) {
        DataStore testDataStore = new DataStore();
        testDataStore.setDataStoreType(dst);
        testDataStore.setDriverClass("org.h2.Driver");
        testDataStore.setName("h2-db-test");
        testDataStore.setPassword("");
        testDataStore.setTableTypes(TableTypes.TABLE_VIEW);
        testDataStore.setUrl("jdbc:h2:~");
        testDataStore.setSchema("test");
        testDataStore.setUsername("");
        return testDataStore;
    }
    
    public static DataStore createMySqlDataStore() {
        DataStore testDataStore = new DataStore();
        testDataStore.setDataStoreType(DataStoreType.SQL);
        testDataStore.setDriverClass("com.mysql.jdbc.Driver");
        testDataStore.setName("employees");
        testDataStore.setPassword("");
        testDataStore.setTableTypes(TableTypes.TABLE_VIEW);
        testDataStore.setUrl("jdbc:mysql://localhost:3306");
        testDataStore.setSchema("employees");
        testDataStore.setUsername("root");
        return testDataStore;
    }
}
