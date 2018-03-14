package mx.infotec.dads.kukulkan.engine.util;

import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for TemplateFinder class.
 *
 * @author erik.valdivieso
 */
public class ListFileUtilTest {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ListFileUtilTest.class);
    
    private final static String PATTERN = ".*class";
    
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
            
            LOGGER.debug("List:\n{}", sb.toString());
        }
        
        return !list.isEmpty();
    }
    
}
