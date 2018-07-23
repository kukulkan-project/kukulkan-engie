/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import mx.infotec.dads.kukulkan.engine.service.FileUtil;

public class FileUtils {

    /**
     * Find the needle in file and inserts the contents before the needle if found
     * 
     * @param file
     *            the text file
     * @param needle
     *            the substring to find
     * @param content
     *            the content to add if needle is found
     * @return
     */
    public static void rewriteFile(String file, String needle, List<String> content) {
        Path filePath = Paths.get(file);
        List<String> haystack;
        try {
            haystack = Files.readAllLines(filePath);
            Integer indexNeedle = getNeedleIndex(haystack, needle);
            if (indexNeedle != null) {
                haystack.addAll(indexNeedle.intValue(), content);
                FileUtil.saveToFile(filePath, haystack);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Find the needle in haystack and returns its index
     * 
     * @param haystack
     *            the content where to find the needle
     * @param needle
     *            the substring to find
     * @return the index needle
     */
    public static Integer getNeedleIndex(List<String> haystack, String needle) {
        for (int i = 0; i < haystack.size(); i++) {
            if (haystack.get(i).contains(needle)) {
                return i;
            }
        }
        return null;
    }

}
