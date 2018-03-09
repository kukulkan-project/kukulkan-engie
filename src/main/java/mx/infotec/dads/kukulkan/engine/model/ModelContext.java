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
package mx.infotec.dads.kukulkan.engine.model;

import java.nio.file.Path;

import mx.infotec.dads.kukulkan.metamodel.editor.Editor;

/**
 * Model Context; It is used int TemplateService.
 *
 * @author Daniel Cortes Pichardo
 */
public class ModelContext {

    /** The template name. */
    private Path templatePath;

    /** The model. */
    private Object model;

    /** The editor. */
    private Editor editor;

    /** The real file path. */
    private Path realFilePath;

    /** The relative file path. */
    private Path relativeFilePath;
    
    private String fileName;
    
    private String[] keywords;

    private ModelContext() {

    }

    /**
     * Gets the template name.
     *
     * @return the template name
     */
    public Path getTemplatePath() {
        return templatePath;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public Object getModel() {
        return model;
    }

    /**
     * Gets the editor.
     *
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Gets the real file path.
     *
     * @return the real file path
     */
    public Path getRealFilePath() {
        return realFilePath;
    }

    /**
     * Gets the relative file path.
     *
     * @return the relative file path
     */
    public Path getRelativeFilePath() {
        return relativeFilePath;
    }

    /**
     * Sets the template name.
     *
     * @param templateName
     *            the new template name
     */
    public void setTemplatePath(Path templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * Sets the model.
     *
     * @param model
     *            the new model
     */
    public void setModel(Object model) {
        this.model = model;
    }

    /**
     * Sets the editor.
     *
     * @param editor
     *            the new editor
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /**
     * Sets the real file path.
     *
     * @param realFilePath
     *            the new real file path
     */
    public void setRealFilePath(Path realFilePath) {
        this.realFilePath = realFilePath;
    }

    /**
     * Sets the relative file path.
     *
     * @param relativeFilePath
     *            the new relative file path
     */
    public void setRelativeFilePath(Path relativeFilePath) {
        this.relativeFilePath = relativeFilePath;
    }

    public static class Builder {
        private Path templatePath;
        private Object model;
        private Editor editor;
        private Path realFilePath;
        private Path relativeFilePath;

        public Builder templatePath(Path templatePath) {
            this.templatePath = templatePath;
            return this;
        }

        public Builder model(Object model) {
            this.model = model;
            return this;
        }

        public Builder editor(Editor editor) {
            this.editor = editor;
            return this;
        }

        public Builder realFilePath(Path realFilePath) {
            this.realFilePath = realFilePath;
            return this;
        }

        public Builder relativeFilePath(Path relativeFilePath) {
            this.relativeFilePath = relativeFilePath;
            return this;
        }

        public ModelContext build() {
            return new ModelContext(this);
        }
    }

    private ModelContext(Builder builder) {
        this.templatePath = builder.templatePath;
        this.model = builder.model;
        this.editor = builder.editor;
        this.realFilePath = builder.realFilePath;
        this.relativeFilePath = builder.relativeFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }
}
