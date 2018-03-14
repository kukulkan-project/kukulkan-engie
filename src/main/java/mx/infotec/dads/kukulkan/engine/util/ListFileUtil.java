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
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util for list files a class source.
 *
 * @author erik.valdivieso
 */
public final class ListFileUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ListFileUtil.class);

    /**
     * Because is a util class, contructor is private.
     */
    private ListFileUtil() {
    }

    /**
     * List files in clazz source (this can be jar o source dir)
     *
     * @param clazz Class to inspect.
     * @return List in class source.
     */
    public static List<String> listFiles(Class clazz) {
        return listFiles(clazz, null);
    }

    /**
     * List files in clazz source (this can be jar o source dir) with pattern.
     *
     * @param clazz Class to inspect.
     * @param pattern Regular expresion to match file name.
     * @return List in class source.
     */
    public static List<String> listFiles(Class clazz, String pattern) {
        return listFiles(clazz, null, pattern);
    }

    /**
     * List files in clazz source (this can be jar o source dir) with pattern.
     *
     * @param clazz Class to inspect.
     * @param subDir Subdir in source.
     * @param pattern Regular expresion to match file name.
     * @return List in class source; is clazz is null, return null.
     */
    public static List<String> listFiles(Class clazz, String subDir, String pattern) {
        if (clazz == null) {
            return null;
        } else {
            CodeSource cs = clazz.getProtectionDomain().getCodeSource();

            if (cs != null) {
                URL url = cs.getLocation();

                LOGGER.debug("Soucer: {}", url.toString());

                if (url.toString().endsWith(".jar")) {
                    return listFilesFromJar(url, fixSubDir(subDir), pattern);
                } else {
                    return listFilesFromDir(url, fixSubDir(subDir), pattern);
                }
            } else {
                LOGGER.warn("Can't get source for class");
            }

            return new ArrayList<>(0);
        }
    }

    private static List<String> listFilesFromJar(URL url, String subDir, String pattern) {
        ZipEntry entry;

        List<String> res = new ArrayList<>();
        String name;
        int idx = getIdx(subDir);

        try (ZipInputStream zip = new ZipInputStream(url.openStream())) {

            while ((entry = zip.getNextEntry()) != null) {
                name = entry.getName();

                if (isValid(name, subDir, pattern)) {
                    res.add(fixName(name, idx));
                }
            }

            return res;
        } catch (IOException ex) {
            LOGGER.error("Can't read file list", ex);
        }

        return new ArrayList<>(0);
    }

    private static int getIdx(String subDir) {
        if (subDir == null) {
            return -1;
        } else {
            return subDir.length();
        }
    }

    private static String fixSubDir(String subDir) {
        if (subDir == null) {
            return null;
        } else if (subDir.endsWith("/")) {
            return subDir;
        } else {
            return subDir + '/';
        }
    }

    private static List<String> listFilesFromDir(URL url, String subDir, String pattern) {
        File dir = new File(url.getFile());

        List<String> res = new ArrayList<>();

        String subDirExtra;

        if (subDir == null) {
            subDirExtra = null;
        } else {
            subDirExtra = dir.getPath() + '/' + subDir;
        }

        int idx = getIdx(subDirExtra);

        addFiles(dir, res, subDirExtra, pattern, idx);

        return res;
    }

    private static void addFiles(File dir, List<String> res, String subDir, String pattern, int idx) {
        String path = dir.getPath();

        if (isValid(path, subDir, pattern)) {
            res.add(fixName(path, idx));
        }

        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                addFiles(f, res, subDir, pattern, idx);
            }
        }
    }

    private static boolean isValid(String path, String subDir, String pattern) {
        return (subDir == null || path.startsWith(subDir)) && (pattern == null || path.matches(pattern));
    }

    private static String fixName(String path, int index) {
        if (index == -1) {
            return path;
        } else {
            return path.substring(index);
        }
    }

}
