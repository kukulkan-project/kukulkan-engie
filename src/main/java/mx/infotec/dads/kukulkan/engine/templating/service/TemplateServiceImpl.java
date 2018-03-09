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
package mx.infotec.dads.kukulkan.engine.templating.service;

import static mx.infotec.dads.kukulkan.engine.util.TemplateUtil.processTemplate;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import mx.infotec.dads.kukulkan.engine.model.ModelContext;
import mx.infotec.dads.kukulkan.engine.util.TemplateUtil;
import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratedElement;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * The Class TemplateServiceImpl.
 *
 * @author Daniel Cortes Pichardo
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class TemplateServiceImpl implements TemplateService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateServiceImpl.class);

    /** The fm configuration. */
    @Autowired
    private Configuration fmConfiguration;

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.engine.templating.service.TemplateService#
     * fillModel(mx.infotec.dads.kukulkan.metamodel.foundation.
     * Entity, java.lang.String, java.lang.String, java.lang.Object,
     * mx.infotec.dads.kukulkan.metamodel.util.BasePathEnum, java.lang.String,
     * mx.infotec.dads.kukulkan.metamodel.editor.Editor, java.nio.file.Path)
     */
    @Override
    public Optional<GeneratedElement> createGeneratedElement(ModelContext context) {
        Optional<Template> templateOptional = TemplateUtil.get(fmConfiguration, context.getTemplatePath().toString());
        if (templateOptional.isPresent()) {
            return Optional.of(processTemplate(templateOptional.get(), context));
        } else {
            LOGGER.error("Template not foud : {}", context.getTemplatePath());
            return Optional.empty();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.engine.templating.service.TemplateService#
     * fillAbstractTemplate(java.lang.String, java.lang.Object)
     */
    @Override
    public String fillTemplate(String templateRelativePath, Object model) {
        Optional<Template> templateOptional = TemplateUtil.get(fmConfiguration, templateRelativePath);
        if (templateOptional.isPresent()) {
            return processTemplate(model, templateOptional.get());
        } else {
            throw new MetaModelException("Template not found : " + templateRelativePath);
        }
    }
}
