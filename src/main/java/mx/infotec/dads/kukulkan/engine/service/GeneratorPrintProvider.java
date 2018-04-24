package mx.infotec.dads.kukulkan.engine.service;

/**
 * Print Provider
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface GeneratorPrintProvider {

    public void info(String message);

    public void info(String message, Object... objects);

    public void warning(String message);

    public void warning(String message, Object... objects);

    public void error(String message);

    public void error(String message, Throwable throwable);

    void error(String message, Object[] objects);
}
