package mx.infotec.dads.kukulkan.engine.dbmigrations;

import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createAtlasDataStore;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createDefaultDataStoreType;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createDefaultPluralRuleType;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createDefaultSingularRuleType;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createEsRule;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createGrammarDataStore;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createGrammarDataStoreType;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createOsRule;
import static mx.infotec.dads.kukulkan.metamodel.util.EntitiesFactory.createTestDataStore;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import mx.infotec.dads.kukulkan.metamodel.foundation.DataStore;
import mx.infotec.dads.kukulkan.metamodel.foundation.DataStoreType;
import mx.infotec.dads.kukulkan.metamodel.foundation.RuleType;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "002")
public class CatalogSetupMigration {

	@ChangeSet(order = "01", author = "dcp", id = "02-kukulkan")
	public void addDataStoreType(MongoTemplate mongoTemplate) {
		DataStoreType dst = createDefaultDataStoreType();
		mongoTemplate.save(dst);
		DataStoreType dstGrammar = createGrammarDataStoreType();
		mongoTemplate.save(dstGrammar);
		DataStore testDs = createTestDataStore(dst);
		mongoTemplate.save(testDs);
		DataStore atlasDs = createAtlasDataStore(dst);
		mongoTemplate.save(atlasDs);
		DataStore grammar = createGrammarDataStore(dstGrammar);
		mongoTemplate.save(grammar);
		RuleType singularRuleType = createDefaultSingularRuleType();
		mongoTemplate.save(singularRuleType);
		mongoTemplate.save(createDefaultPluralRuleType());
		mongoTemplate.save(createOsRule(singularRuleType));
		mongoTemplate.save(createEsRule(singularRuleType));
	}
}
