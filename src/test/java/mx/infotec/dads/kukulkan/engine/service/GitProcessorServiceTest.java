package mx.infotec.dads.kukulkan.engine.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@ContextConfiguration(classes={GitProcessorService.class, GitProcessorServiceImpl.class})
public class GitProcessorServiceTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(GitProcessorServiceTest.class);

    @Autowired
    private GitProcessorService gitProcessorService;

    @Test
    public void tmp() {
        assertNotNull(gitProcessorService);

        try {
            File repositoryDir = File.createTempFile("git", "-test");
            repositoryDir.delete();
            repositoryDir.mkdirs();

            Git git = gitProcessorService.init(repositoryDir);

            File gitFolder = new File(repositoryDir.getAbsolutePath() + "/.git");
            assertTrue(gitFolder.exists());

            File aFile = File.createTempFile("aFile", ".txr", repositoryDir);

            gitProcessorService.addFiles(git, ".");
            gitProcessorService.commit(git, "Firts", new PersonIdent("John Doe", "john@aol.com"));

            System.out.println("===============================\n\n------------------------\n" + repositoryDir);

            gitProcessorService.createBranch(git, "develop");



        } catch (Exception ex) {
            LOGGER.error("Error at test git", ex);
            assert false;
        }

    }

}