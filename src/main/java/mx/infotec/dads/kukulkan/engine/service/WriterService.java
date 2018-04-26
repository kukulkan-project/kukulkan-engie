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

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public interface WriterService {

    /**
     * Fill the template with the model and save to path resolved to relative. The
     * relative is processed as a template
     */
    public Optional<File> copyTemplate(String template, Path path, String relative, Object model);

    /**
     * Copy the resource to the path resolved to relative
     */
    public Optional<File> copy(String resource, Path path, String relative);

    /**
     * Copy the resource path resolved to relative. The relative is processed as a
     * template.
     */
    public Optional<File> copy(String resource, Path path, String relative, Object model);

    /**
     * Search in jar containing the class, then copy the files in directory matching
     * with the pattern and save into current path of navigator resolved to relative
     * 
     */
    public void copyDir(Class clazz, String directory, String pattern, Path path, String relative);

    /**
     * Search in jar containing the class, then copy all files in directory and save
     * them into current path of navigator resolved to relative
     */
    public void copyDir(Class clazz, String directory, Path path, String relative);

    /**
     * Search in jar containing the class, then copy all files in directory and save
     * them into current path of navigator resolved to relative
     * 
     * The relative is processed as a template
     */
    public void copyDir(Class clazz, String directory, Path path, String relative, Object model);

    /**
     * Search in jar containing the class, then copy the files in directory matching
     * with the pattern and save into current path of navigator resolved to relative
     * 
     * The relative is processed as a template
     */
    public void copyDir(Class clazz, String directory, String pattern, Path path, String relative, Object model);

    /**
     * TODO
     */
    public void copySmart(String template, Path path, String relative, Object model);

}