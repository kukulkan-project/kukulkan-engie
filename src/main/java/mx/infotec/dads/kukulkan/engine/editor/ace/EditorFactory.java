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

import mx.infotec.dads.kukulkan.metamodel.editor.LanguageType;

/**
 * Editor Factory used for create common Editor Objects.
 *
 * @author Daniel Cortes Pichardo
 */
public class EditorFactory {

    /**
     * Instantiates a new editor factory.
     */
    private EditorFactory() {

    }

    /**
     * It create a Default instance of an AceEditor implementation.
     *
     * @param languageType the language type
     * @return an AceEditor
     */
    public static AceEditor createDefaultAceEditor(LanguageType languageType) {
        return new AceEditor.AceEditorBuilder().fistLineNumber(1).isReadOnly(true).showGutter(true)
                .withTheme("twilight").withLanguage(languageType.language()).build();
    }
}
