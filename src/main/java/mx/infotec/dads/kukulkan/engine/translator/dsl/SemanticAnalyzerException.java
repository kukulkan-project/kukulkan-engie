package mx.infotec.dads.kukulkan.engine.translator.dsl;

/**
 * {@link SchemaAnalyzerException}
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SemanticAnalyzerException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new meta model exception.
     */
    public SemanticAnalyzerException() {
        super();
    }

    /**
     * Instantiates a new meta model exception.
     *
     * @param s
     *            the s
     */
    public SemanticAnalyzerException(String s) {
        super(s);
    }

    /**
     * Instantiates a new meta model exception.
     *
     * @param s
     *            the s
     * @param throwable
     *            the throwable
     */
    public SemanticAnalyzerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Instantiates a new meta model exception.
     *
     * @param throwable
     *            the throwable
     */
    public SemanticAnalyzerException(Throwable throwable) {
        super(throwable);
    }
}
