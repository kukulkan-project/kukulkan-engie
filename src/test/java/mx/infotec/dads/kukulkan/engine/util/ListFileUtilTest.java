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

import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for TemplateFinder class.
 *
 * @author erik.valdivieso
 */
public class ListFileUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListFileUtilTest.class);

    private static final String PATTERN = null;

    /**
     * Make a simple test for listFiles method.
     */
    @Test
    public void listFilesTestFromJar() {
        assert test(Test.class, null) : "Can't list files from jar";
    }

    /**
     * Make a simple test for listFiles method.
     */
    @Test
    public void listFilesTestFromDir() {
        assert test(ListFileUtil.class, null) : "Can't list files from source dir";
    }

    /**
     * Make a simple test for listFiles method.
     */
    @Test
    public void listFilesTestFromJarWithSubDir() {
        assert test(Test.class, "org/junit/runners") : "Can't list files from jar";
    }

    /**
     * Make a simple test for listFiles method.
     */
    @Test
    public void listFilesTestFromDirWithSubDir() {
        assert test(ListFileUtil.class, "mx/infotec/dads/kukulkan/engine/translator") : "Can't list files from source dir";
    }

    private boolean test(Class clazz, String subDir) {
        List<String> list = ListFileUtil.listFiles(clazz, subDir, PATTERN);

        if (LOGGER.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();

            list.forEach((s) -> sb.append(s).append('\n'));

            LOGGER.debug("List:\n{}", sb);
        }

        return !list.isEmpty();
    }

}
