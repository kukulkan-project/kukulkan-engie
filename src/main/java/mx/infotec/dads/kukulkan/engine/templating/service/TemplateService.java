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

import java.nio.file.Path;

import mx.infotec.dads.kukulkan.metamodel.editor.Editor;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelElement;
import mx.infotec.dads.kukulkan.metamodel.util.BasePathEnum;

/**
 * The Interface TemplateService.
 *
 * @author Daniel Cortes Pichardo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface TemplateService {

    /**
     * Method used for fill a template with the proper entity.
     *
     * @param dme the dme
     * @param projectId the project id
     * @param templateName the template name
     * @param model the model
     * @param path the path
     * @param filePath the file path
     * @param editor the editor
     * @param outputDirs the output dirs
     */
    void fillModel(DomainModelElement dme, String projectId, String templateName, Object model, BasePathEnum path,
            String filePath, Editor editor, Path outputDirs);

    /**
     * Method used for fill a template with the proper entity.
     *
     * @param projectId the project id
     * @param templateName the template name
     * @param model the model
     * @param path the path
     * @param filePath the file path
     * @param editor the editor
     * @param outputDir the output dir
     */
    void fillModel(String projectId, String templateName, Object model, BasePathEnum path, String filePath,
            Editor editor, Path outputDir);

    /**
     * Method used for fill a template with the proper entity.
     *
     * @param dme the dme
     * @param proyectoId the proyecto id
     * @param templateName the template name
     * @param model the model
     * @param basePath the base path
     * @param filePath the file path
     * @param editor the editor
     * @param outputDir the output dir
     */
    void fillModel(DomainModel dme, String proyectoId, String templateName, Object model, BasePathEnum basePath,
            String filePath, Editor editor, Path outputDir);

    /**
     * Method used for fill a template with the proper entity.
     *
     * @param dme the dme
     * @param proyectoId the proyecto id
     * @param templateName the template name
     * @param model the model
     * @param basePath the base path
     * @param filePath the file path
     * @param outputDir the output dir
     */
    void fillModel(DomainModelElement dme, String proyectoId, String templateName, Object model, BasePathEnum basePath,
            String filePath, Path outputDir);

    /**
     * Method used for fill a template with the proper entity.
     *
     * @param templateRelativePath the template relative path
     * @param model the model
     * @return the string
     */
    String fillAbstractTemplate(String templateRelativePath, Object model);
}
