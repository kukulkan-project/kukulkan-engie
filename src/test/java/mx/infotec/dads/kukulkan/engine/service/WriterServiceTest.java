package mx.infotec.dads.kukulkan.engine.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import freemarker.template.Configuration;
import mx.infotec.dads.kukulkan.engine.config.PrintProviderConfiguration;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Configuration.class, TemplateServiceImpl.class, WriterServiceImpl.class, FileUtil.class })
@Import(PrintProviderConfiguration.class)
public class WriterServiceTest {

    private static final String writerTestFolder = "writertestresources";

    @Autowired
    WriterServiceImpl writerService;

    @Test
    public void testRewriteFile() throws IOException, URISyntaxException {
        URL fileToRewrite = Resources.getResource("file-to-rewrite.txt");
        URL fileRewrited = Resources.getResource("file-rewrited.txt");
        writerService.rewriteFile("target/test-classes/template.ftl", Paths.get(fileToRewrite.toURI()), new Object(),
                "kukulkan-needle");
        assertEquals(Resources.toString(fileRewrited, Charsets.UTF_8),
                Resources.toString(fileToRewrite, Charsets.UTF_8));
    }

    @Test
    public void testAddMavenDependency() throws URISyntaxException, IOException {
        URL projectFolder = Resources.getResource(writerTestFolder);
        URL fileToRewrite = Resources.getResource(writerTestFolder + "/pom.xml");
        URL fileRewrited = Resources.getResource(writerTestFolder + "/pom-rewrited.xml");
        writerService.addMavenDependency("target/test-classes/" + writerTestFolder + "/example-maven-dependency.ftl", Paths.get(projectFolder.toURI()));
        assertEquals(Resources.toString(fileRewrited, Charsets.UTF_8),
                Resources.toString(fileToRewrite, Charsets.UTF_8));
    }

}