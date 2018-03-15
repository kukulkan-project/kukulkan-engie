/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.kukulkan.engine.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import mx.infotec.dads.kukulkan.engine.model.ModelContext;
import mx.infotec.dads.kukulkan.metamodel.context.BaseContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratedElement;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateInfo;
import mx.infotec.dads.kukulkan.metamodel.template.TemplateType;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;
import mx.infotec.dads.kukulkan.metamodel.util.StringFormater;

/**
 * TemplateUtil class used for basic operation with freemarker.
 *
 * @author Daniel Cortes Pichardo
 */
public class TemplateUtil {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtil.class);

    /**
     * Instantiates a new template util.
     */
    private TemplateUtil() {
    }

    /**
     * Get a template by name using the configuration object. This method handle
     * the IOexception that the configuration object could throws.
     *
     * @param config
     *            the config
     * @param templateName
     *            the template name
     * @return the optional
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
     * It process a template and transform it into a GeneratedElement object.
     *
     * @param template
     *            the template
     * @param context
     *            the context
     * @return GeneratedElement from a Template
     */
    public static GeneratedElement processTemplate(Template template, ModelContext context) {
        GeneratedElement ge = new GeneratedElement(context.getRealFilePath(), context.getRelativeFilePath(),
                processTemplate(context.getModel(), template), context.getEditor());
        ge.setKeywords(context.getKeywords());
        ge.setFileName(context.getFileName());
        return ge;
    }

    /**
     * It process a Template and fill it with the model object,.
     *
     * @param model
     *            the model
     * @param template
     *            the template
     * @return the string
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
     * It process a Template and save it into the specified path.
     *
     * @param model
     *            the model
     * @param template
     *            the template
     * @param pathToSave
     *            the path to save
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
