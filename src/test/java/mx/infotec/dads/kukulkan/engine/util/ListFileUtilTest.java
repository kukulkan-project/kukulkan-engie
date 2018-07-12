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
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for TemplateFinder class.
 *
 * @author erik.valdivieso
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE, classes=ListFileUtil.class)
public class ListFileUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListFileUtilTest.class);

    private static final String PATTERN = ".*class";
    private static final String FAKE_JAR = "/lib/fake.jar";

    private static String fakeJarPath;

    //private static final String PATTERN2 = "jar:file:/var/lib/kukulkan/kukulkan-shell-terminal-0.0.1-RELEASE.jar!/BOOT-INF/lib/kukulkan-shell-plugin-chatbot-0.0.1-RELEASE.jar!/";
    @BeforeClass
    public static void init() {
        File tmp = new File("src/test/resources" + FAKE_JAR);

        if (tmp.exists()) {
            fakeJarPath = "jar:file:" + tmp.getAbsolutePath() + "!/BOOT-INF/lib/velocity-1.7.jar!/";

            LOGGER.debug("Fake jar: {}", fakeJarPath);
        } else {
            LOGGER.warn("Can't resolve {}", FAKE_JAR);

            fakeJarPath = null;
        }
    }

    /**
     * Make a simple test for listFiles method.
     */
    @Test
    public void listWithNull() {
        List<String> list = ListFileUtil.listFiles(null);
        assert list != null : "List is null object";
        assert list.isEmpty() : "Spec empty list";
        print(list);
    }

    /**
     * Make a simple test for listFiles method.
     */
    @Test
    public void listAllFiles() {
        List<String> list = ListFileUtil.listFiles(Test.class);
        assert list != null : "List is null object";
        assert !list.isEmpty() : "Can't list files";
        print(list);
    }

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
    public void listFilesTestFromDoubleJar() {
        assert fakeJarPath != null : "Required fake jar path";
        List<String> list = ListFileUtil.listFilesFromDoubleJar(fakeJarPath, "org/apache/velocity/runtime/", null);
        assert !list.isEmpty() : "Can't list files";
        print(list);
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
        assert test(Test.class, "org/junit/runners/") : "Can't list files from jar";
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

        if (list != null) {
            print(list);

            return !list.isEmpty();
        } else {
            return false;
        }
    }

    private void print(List<String> list) {
        if (LOGGER.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();

            list.forEach((s) -> sb.append(s).append('\n'));

            LOGGER.debug("List:\n{}", sb);
        }
    }

}
