package mx.infotec.dads.kukulkan.engine.translator.dsl;

import org.antlr.v4.runtime.Token;

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
    private String sourcePropertyName;
    private String target;
    private String targetPropertyName;

    private AssociationType type;

    public Association(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public AssociationType getType() {
        return type;
    }

    public void setType(AssociationType type) {
        this.type = type;
    }

    public String getSourcePropertyName() {
        return sourcePropertyName;
    }

    public void setSourcePropertyName(Token id) {
        this.sourcePropertyName = id.getText();
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public void setTargetPropertyName(String targetPropertyName) {
        this.targetPropertyName = targetPropertyName;
    }

    public void setTargetPropertyName(Token token) {
        if (token != null) {
            this.targetPropertyName = token.getText();
        }
    }

    public boolean isBidirectional() {
        return targetPropertyName != null;
    }

    @Override
    public String toString() {
        return "[source: " + source + ", " + "propertyName: " + sourcePropertyName + "], " + "[target: " + target + ", "
                + "propertyName: " + targetPropertyName + "], " + "[type: " + type + "]";
    }

}
