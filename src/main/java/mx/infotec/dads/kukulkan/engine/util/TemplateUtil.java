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

	public static Optional<Template> get(Configuration config, String templateName) {
		Template template = null;
		try {
			template = config.getTemplate(templateName);
		} catch (IOException e) {
			LOGGER.error("TemplateUtil.get() Error", e);
		}
		return Optional.ofNullable(template);
	}

	public static GeneratedElement processTemplate(Object model, Template template, Path path, String simplePath,
			Editor editor) {
		return new GeneratedElement(path, simplePath, processTemplate(model, template), editor);
	}

	public static String processTemplate(Object model, Template template) {
		try (StringWriter stringWriter = new StringWriter()) {
			template.process(model, stringWriter);
			return stringWriter.toString();
		} catch (IOException | TemplateException e) {
			throw new MetaModelException("Fill Model Error", e);
		}
	}

	public static void processTemplate(Object model, Template template, Path pathToSave) {
		try (StringWriter stringWriter = new StringWriter()) {
			LOGGER.info("Generating to: {}", pathToSave.normalize().toFile());
			template.process(model, stringWriter);
		} catch (IOException | TemplateException e) {
			throw new MetaModelException("Fill Model Error", e);
		}

	}
}
