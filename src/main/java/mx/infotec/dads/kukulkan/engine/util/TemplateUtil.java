package mx.infotec.dads.kukulkan.engine.util;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import mx.infotec.dads.kukulkan.metamodel.editor.Editor;
import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratedElement;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * TemplateUtil class used for basic operation with freemarker
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class TemplateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtil.class);

    private TemplateUtil() {
    }

    /**
     * Get a template by name using the configuration object. This method handle
     * the IOexception that the configuration object could throws.
     * 
     * @param config
     * @param templateName
     * @return
     */
    public static Optional<Template> get(Configuration config, String templateName) {
        Template template = null;
        try {
            template = config.getTemplate(templateName);
        } catch (IOException e) {
            LOGGER.error("TemplateUtil.get() Error", e);
        }
        return Optional.ofNullable(template);
    }

    /**
     * It process a template and transform it into a GeneratedElement object
     * 
     * @param model
     * @param template
     * @param path
     * @param simplePath
     * @param editor
     * @return GeneratedElement from a Template
     */
    public static GeneratedElement processTemplate(Object model, Template template, Path path, String simplePath,
            Editor editor) {
        return new GeneratedElement(path, simplePath, processTemplate(model, template), editor);
    }

    /**
     * It process a Template and fill it with the model object,.
     * 
     * @param model
     * @param template
     * @return
     */

    public static String processTemplate(Object model, Template template) {
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(model, stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new MetaModelException("Fill Model Error", e);
        }
    }

    /**
     * It process a Template and save it into the specified path
     * 
     * @param model
     * @param template
     * @param pathToSave
     */
    public static void processTemplate(Object model, Template template, Path pathToSave) {
        try (StringWriter stringWriter = new StringWriter()) {
            LOGGER.info("Generating to: {}", pathToSave.normalize().toFile());
            template.process(model, stringWriter);
        } catch (IOException | TemplateException e) {
            throw new MetaModelException("Fill Model Error", e);
        }
    }
}
