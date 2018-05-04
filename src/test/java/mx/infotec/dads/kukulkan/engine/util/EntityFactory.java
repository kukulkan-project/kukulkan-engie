package mx.infotec.dads.kukulkan.engine.util;

import java.util.ArrayList;
import java.util.List;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarSemanticAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarUtil;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.DomainModelContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;

public class EntityFactory {

    /**
     * createSingleDataModelGroupList.
     *
     * @param visitor
     *            the visitor
     * @return the list
     */
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
}
