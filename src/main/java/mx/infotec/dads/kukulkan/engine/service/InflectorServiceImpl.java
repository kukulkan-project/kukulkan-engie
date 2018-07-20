/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
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

package mx.infotec.dads.kukulkan.engine.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.infotec.dads.nlp.inflector.core.Inflector;

/**
 * The multi-language inflector service
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@Service("defaultInflectorService")
public class InflectorServiceImpl implements InflectorService {

    @Autowired
    @Qualifier("inflectorEnglish")
    private Inflector englishInflector;

    @Autowired
    @Qualifier("inflectorSpanish")
    private Inflector spanishInflector;

    /**
     * Singularize the word in Spanish
     * 
     * @param word
     *            to singularize
     * @return the singular (if word not null) or the word itself if it could not
     *         been singularized
     */
    @Override
    public String singularize(String word) {
        return resultOrWord(word, spanishInflector.singularize(word));
    }

    /**
     * Pluralize the word in Spanish
     * 
     * @param word
     *            to pluralize
     * @return the plural (if word not null) or the word itself if it could not been
     *         singularized
     */
    @Override
    public String pluralize(String word) {
        return resultOrWord(word, spanishInflector.pluralize(word));
    }

    /**
     * Singularize the word in given language
     * 
     * @param word
     *            to singularize
     * @param locale
     *            for regionalization
     * @return the singular (if word not null) or the word itself if it could not
     *         been singularized
     */
    @Override
    public String singularize(String word, Locale locale) throws UnsupportedLanguage {
        Inflector inflector = inflectorSelector(locale);
        return resultOrWord(word, inflector.singularize(word));
    }

    /**
     * Pluralize the word in given language
     * 
     * @param word
     *            to pluralize
     * @param locale
     *            for regionalization
     * @return the plural (if word not null) or the word itself if it could not been
     *         singularized
     */
    @Override
    public String pluralize(String word, Locale locale) throws UnsupportedLanguage {
        Inflector inflector = inflectorSelector(locale);
        return resultOrWord(word, inflector.pluralize(word));
    }

    /**
     * Selects an inflector for the given locale
     * 
     * @param locale
     * @return
     * @throws UnsupportedLanguage
     */
    private Inflector inflectorSelector(Locale locale) throws UnsupportedLanguage {
        Inflector inflector = null;
        String language = locale.getLanguage();
        switch (language) {
        case "en":
            inflector = englishInflector;
            break;

        case "es":
            inflector = spanishInflector;
            break;

        default:
            break;
        }

        if (inflector != null)
            return inflector;
        throw new UnsupportedLanguage("Unsupported language " + language);

    }

    /**
     * 
     * @param word
     * @param result
     * @return
     */
    private static String resultOrWord(String word, String result) {
        if (word == null) {
            return null;
        }
        if (result == null) {
            return word;
        } else {
            return result;
        }
    }

}
