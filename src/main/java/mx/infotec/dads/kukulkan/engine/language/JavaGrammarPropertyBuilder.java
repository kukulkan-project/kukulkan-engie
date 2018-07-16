package mx.infotec.dads.kukulkan.engine.language;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.FieldTypeContext;

/**
 * The Class JavaGrammarPropertyBuilder.
 */
public class JavaGrammarPropertyBuilder extends BaseJavaPropertyBuilder {

    @Override
    public PropertyBuilder<JavaProperty> addType(FieldTypeContext type) {
        GrammarMapping.addType(getJavaProperty(), type);
        return this;
    }

}
