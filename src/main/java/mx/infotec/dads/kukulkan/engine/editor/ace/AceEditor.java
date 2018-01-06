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
package mx.infotec.dads.kukulkan.engine.editor.ace;

import mx.infotec.dads.kukulkan.metamodel.editor.Editor;

/**
 * AceEditor implements the Ace Editor methods.
 *
 * @author Daniel Cortes Pichardo
 */
public class AceEditor implements Editor {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The theme. */
    private String theme;
    
    /** The language. */
    private String language;
    
    /** The read only. */
    private boolean readOnly;
    
    /** The show gutter. */
    private boolean showGutter;
    
    /** The first line number. */
    private int firstLineNumber;

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.metamodel.editor.Editor#getTheme()
     */
    @Override
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme.
     *
     * @param theme the new theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.metamodel.editor.Editor#getLanguage()
     */
    @Override
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     *
     * @param language the new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.metamodel.editor.Editor#isReadOnly()
     */
    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets the read only.
     *
     * @param readOnly the new read only
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.metamodel.editor.Editor#isShowGutter()
     */
    @Override
    public boolean isShowGutter() {
        return showGutter;
    }

    /**
     * Sets the show gutter.
     *
     * @param showGutter the new show gutter
     */
    public void setShowGutter(boolean showGutter) {
        this.showGutter = showGutter;
    }

    /* (non-Javadoc)
     * @see mx.infotec.dads.kukulkan.metamodel.editor.Editor#getFirstLineNumber()
     */
    @Override
    public int getFirstLineNumber() {
        return firstLineNumber;
    }

    /**
     * Sets the first line number.
     *
     * @param firstLineNumber the new first line number
     */
    public void setFirstLineNumber(int firstLineNumber) {
        this.firstLineNumber = firstLineNumber;
    }

    /**
     * The Class AceEditorBuilder.
     */
    public static class AceEditorBuilder {
        
        /** The theme. */
        // optional
        private String theme;
        
        /** The language. */
        private String language;
        
        /** The read only. */
        private boolean readOnly;
        
        /** The show gutter. */
        private boolean showGutter;
        
        /** The first line number. */
        private int firstLineNumber;

        /**
         * With theme.
         *
         * @param theme the theme
         * @return the ace editor builder
         */
        public AceEditorBuilder withTheme(String theme) {
            this.theme = theme;
            return this;
        }

        /**
         * With language.
         *
         * @param language the language
         * @return the ace editor builder
         */
        public AceEditorBuilder withLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * Checks if is read only.
         *
         * @param readOnly the read only
         * @return the ace editor builder
         */
        public AceEditorBuilder isReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        /**
         * Show gutter.
         *
         * @param showGutter the show gutter
         * @return the ace editor builder
         */
        public AceEditorBuilder showGutter(boolean showGutter) {
            this.showGutter = showGutter;
            return this;
        }

        /**
         * Fist line number.
         *
         * @param firstLineNumber the first line number
         * @return the ace editor builder
         */
        public AceEditorBuilder fistLineNumber(int firstLineNumber) {
            this.firstLineNumber = firstLineNumber;
            return this;
        }

        /**
         * Builds the.
         *
         * @return the ace editor
         */
        public AceEditor build() {
            AceEditor editor = new AceEditor();
            editor.setLanguage(this.language);
            editor.setFirstLineNumber(this.firstLineNumber);
            editor.setReadOnly(this.readOnly);
            editor.setShowGutter(this.showGutter);
            editor.setTheme(this.theme);
            return editor;
        }

    }
}
