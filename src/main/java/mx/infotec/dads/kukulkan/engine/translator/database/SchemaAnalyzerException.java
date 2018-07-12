package mx.infotec.dads.kukulkan.engine.translator.database;

/**
 * {@link SchemaAnalyzerException}
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SchemaAnalyzerException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new meta model exception.
     */
    public SchemaAnalyzerException() {
        super();
    }

    /**
     * Instantiates a new meta model exception.
     *
     * @param s
     *            the s
     */
    public SchemaAnalyzerException(String s) {
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
    public SchemaAnalyzerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Instantiates a new meta model exception.
     *
     * @param throwable
     *            the throwable
     */
    public SchemaAnalyzerException(Throwable throwable) {
        super(throwable);
    }
}
