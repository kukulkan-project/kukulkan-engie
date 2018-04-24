package mx.infotec.dads.kukulkan.engine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Default Generator Print Provider
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DefaultGeneratorPrintProvider implements GeneratorPrintProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, Object... objects) {
        LOGGER.info(message, objects);
    }

    @Override
    public void warning(String message) {
        LOGGER.warn(message);

    }

    @Override
    public void warning(String message, Object... objects) {
        LOGGER.warn(message, objects);

    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    @Override
    public void error(String message, Object... objects) {
        LOGGER.error(message, objects);
    }

}
