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
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util for list files a class source.
 *
 * @author erik.valdivieso
 */
public final class ListFileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListFileUtil.class);

    /**
     * Because is a util class, contructor is private.
     */
    private ListFileUtil() {
    }

    /**
     * List files in clazz source (this can be jar o source dir)
     *
     * @param clazz
     *            Class to inspect.
     * @return List in class source.
     */
    public static List<String> listFiles(Class clazz) {
        return listFiles(clazz, null);
    }

    /**
     * List files in clazz source (this can be jar o source dir) with pattern.
     *
     * @param clazz
     *            Class to inspect.
     * @param pattern
     *            Regular expresion to match file name.
     * @return List in class source.
     */
    public static List<String> listFiles(Class clazz, String pattern) {
        return listFiles(clazz, null, pattern);
    }

    /**
     * List files in clazz source (this can be jar o source dir) with pattern.
     *
     * @param clazz
     *            Class to inspect.
     * @param subDir
     *            Subdir in source.
     * @param pattern
     *            Regular expresion to match file name.
     * @return List of files in the source.
     */
    public static List<String> listFiles(Class clazz, String subDir, String pattern) {
        if (clazz != null) {
            CodeSource cs = clazz.getProtectionDomain().getCodeSource();

            if (cs != null) {
                URL url = cs.getLocation();

                String tmp = url.toString();
                LOGGER.debug("Soucer: {}", tmp);

                if (tmp.endsWith("jar!/")) {
                    return listFilesFromDoubleJar(tmp, fixSubDir(subDir), pattern);
                } else if (tmp.endsWith(".jar")) {
                    return listFilesFromJar(url, fixSubDir(subDir), pattern);
                } else {
                    return listFilesFromDir(url, fixSubDir(subDir), pattern);
                }
            } else {
                LOGGER.warn("Can't get source for class");
            }

        }

        return new ArrayList<>(0);
    }

    protected static List<String> listFilesFromDoubleJar(String jarPath, String subDir, String pattern) {
        if (jarPath.startsWith("jar:file:")) {
            return listFilesFromDoubleJar(jarPath.substring(9), subDir, pattern);
        } else if (jarPath.startsWith("file:")) {
            return listFilesFromDoubleJar(jarPath.substring(5), subDir, pattern);
        } else {
            int index = jarPath.indexOf(".jar!/") + 6;

            String first = jarPath.substring(0, index - 2);
            String second = jarPath.substring(index);

            if (second.endsWith("!/")) {
                second = second.substring(0, second.length() - 2);
            } else if (second.endsWith("!")) {
                second = second.substring(0, second.length() - 1);
            }

            try (ZipFile zip = new ZipFile(first)) {
                Enumeration files = zip.entries();
                ZipEntry zipEntry;

                while (files.hasMoreElements()) {
                    zipEntry = (ZipEntry) files.nextElement();

                    if (zipEntry.getName().equals(second)) {
                        try (ZipInputStream zis = new ZipInputStream(zip.getInputStream(zipEntry))) {
                            return listZip(zis, subDir, pattern);
                        }
                    }
                }

                LOGGER.error("Can't find jar {} inside {}", second, first);
            } catch (IOException ex) {
                LOGGER.error("Cant read jar", ex);
            }

            return new ArrayList<>(0);
        }
    }

    private static List<String> listFilesFromJar(URL url, String subDir, String pattern) {
        try (ZipInputStream zip = new ZipInputStream(url.openStream())) {
            return listZip(zip, subDir, pattern);
        } catch (IOException ex) {
            LOGGER.error("Can't read file list", ex);
        }

        return new ArrayList<>(0);
    }

    private static List<String> listZip(ZipInputStream zip, String subDir, String pattern) throws IOException {
        ZipEntry entry;

        List<String> res = new ArrayList<>();
        String name;
        int idx = getIdx(subDir);

        while ((entry = zip.getNextEntry()) != null) {
            name = entry.getName();

            if (!entry.isDirectory() && isValid(name, subDir, pattern)) {
                res.add(fixName(name, idx));
            }
        }

        return res;
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
            subDirExtra = Paths.get(dir.getPath()).resolve(subDir).toString();
        }

        int idx = getIdx(subDirExtra);

        addFiles(dir, res, subDirExtra, pattern, idx);

        return res;
    }

    private static void addFiles(File dir, List<String> res, String subDir, String pattern, int idx) {
        String path = dir.getPath();

        if (!dir.isDirectory()) {
            if (isValid(path, subDir, pattern)) {
                res.add(fixName(path, idx));
            }
        } else {
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
