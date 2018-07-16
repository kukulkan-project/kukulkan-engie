package mx.infotec.dads.kukulkan.engine.language;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.FieldTypeContext;

/**
 * JavaSchemaPropertyBuilder
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class JavaSchemaPropertyBuilder extends BaseJavaPropertyBuilder {

    @Override
    public PropertyBuilder<JavaProperty> addType(FieldTypeContext type) {
        GrammarMapping.addType(getJavaProperty(), type);
        return this;
    }
}
