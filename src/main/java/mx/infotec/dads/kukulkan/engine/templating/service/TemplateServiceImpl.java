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

import static mx.infotec.dads.kukulkan.engine.editor.ace.EditorFactory.createDefaultAceEditor;
import static mx.infotec.dads.kukulkan.engine.util.TemplateUtil.processTemplate;
import static mx.infotec.dads.kukulkan.metamodel.editor.LanguageType.JAVA;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import mx.infotec.dads.kukulkan.engine.util.TemplateUtil;
import mx.infotec.dads.kukulkan.metamodel.editor.Editor;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelElement;
import mx.infotec.dads.kukulkan.metamodel.foundation.GeneratedElement;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.util.BasePathEnum;
import mx.infotec.dads.kukulkan.metamodel.util.FileUtil;
import mx.infotec.dads.kukulkan.metamodel.util.KukulkanConfigurationProperties;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * 
 * @author Daniel Cortes Pichardo
 * @since 1.0.0
 * @version 1.0.0
 */

@Service
public class TemplateServiceImpl implements TemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    private Configuration fmConfiguration;

    @Override
    public void fillModel(DomainModelElement dme, String proyectoId, String templateName, Object model,
            BasePathEnum basePath, String filePath, Path outputDir) {
        fillModel(dme, proyectoId, templateName, model, basePath, filePath, createDefaultAceEditor(JAVA), outputDir);
    }

    @Override
    public void fillModel(DomainModelElement dme, String proyectoId, String templateName, Object model,
            BasePathEnum basePath, String filePath, Editor editor, Path outputDir) {
        Optional<Template> templateOptional = TemplateUtil.get(fmConfiguration, templateName);
        if (templateOptional.isPresent()) {
            Path path = FileUtil.buildPath(proyectoId, basePath, filePath, outputDir.toString());
            String simplePath = basePath.getPath() + filePath;
            dme.addGeneratedElement(processTemplate(model, templateOptional.get(), path, simplePath, editor));
        }
    }

    @Override
    public void fillModel(DomainModel dm, String proyectoId, String templateName, Object model, BasePathEnum basePath,
            String filePath, Editor editor, Path outputDir) {
        Optional<Template> templateOptional = TemplateUtil.get(fmConfiguration, templateName);
        if (templateOptional.isPresent()) {
            Path path = FileUtil.buildPath(proyectoId, basePath, filePath, outputDir.toString());
            String simplePath = basePath.getPath() + filePath;
            dm.addGeneratedElement(processTemplate(model, templateOptional.get(), path, simplePath, editor));
        } else {
            LOGGER.warn("Template not found for {}", templateName);
        }
    }

    @Override
    public void fillModel(String proyectoId, String templateName, Object model, BasePathEnum basePath, String filePath,
            Editor editor, Path outputDir) {
        TemplateUtil.get(fmConfiguration, templateName).ifPresent(template -> {
            Path path = FileUtil.buildPath(proyectoId, basePath, filePath, outputDir.toString());
            String simplePath = basePath.getPath() + filePath;
            processTemplate(model, template, path, simplePath, editor);
        });
    }

    @Override
    public String fillAbstractTemplate(String templateRelativePath, Object model) {
        Optional<Template> templateOptional = TemplateUtil.get(fmConfiguration, templateRelativePath);
        if (templateOptional.isPresent()) {
            return processTemplate(model, templateOptional.get());
        } else {
            throw new MetaModelException("Template not found for " + templateRelativePath);
        }
    }
}
