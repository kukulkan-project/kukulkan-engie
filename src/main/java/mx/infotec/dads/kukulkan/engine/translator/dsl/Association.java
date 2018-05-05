package mx.infotec.dads.kukulkan.engine.translator.dsl;

import mx.infotec.dads.kukulkan.metamodel.foundation.AssociationType;
import mx.infotec.dads.kukulkan.metamodel.foundation.EntityAssociation;

/**
 * Association is a Container used by {@link GrammarSemanticAnalyzer} before to
 * be used in {@link EntityAssociation}. This is not a real Association, it is
 * just a placeholder before to use a real association, so this class must not
 * be used outside of the {@link GrammarSemanticAnalyzer} context
 * 
 * @author Daniel Cortes Pichardo
 *
 */
class Association {

    private String source;
    private String target;

    private AssociationType type;

    public Association(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public AssociationType getType() {
        return type;
    }

    public void setType(AssociationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[source: " + source + ", " + "target: " + target + ", " + "type: " + type + "]";
    }
}
