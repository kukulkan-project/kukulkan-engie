package mx.infotec.dads.kukulkan.engine.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

@Service
public class GitProcessorServiceImpl implements GitProcessorService {

	@Override
	public Git init(String pathname) throws GitProcessorException, GitAPIException, IOException {
        return init(new File(pathname));
	}

	@Override
	public Git init(File directory) throws GitProcessorException, GitAPIException, IOException {
        File subDir = new File(directory.getAbsolutePath() + "/.git");

        if (subDir.exists()) {
            return Git.open(directory);
        } else {
            return Git.init().setDirectory(directory).call();
        }
	}

	@Override
	public DirCache addFiles(Git gitRepository, String... filePaths) throws GitProcessorException, GitAPIException {
        AddCommand addCommand = gitRepository.add();
        for (String filePath: filePaths) {
            addCommand.addFilepattern(filePath);
        }
        return addCommand.call();
	}

	@Override
	public Ref createBranch(Git gitRepository, String branchName) throws GitAPIException {
        Ref ref = findReference(gitRepository, branchName);

        if (ref == null) {
            ref = gitRepository.branchCreate().setName(branchName).call();
        }

        return ref;
    }

	@Override
	public Ref switchBranch(Git gitRepository, String branchName) throws GitAPIException {
		return null;
    }
    
    private Ref findReference(Git gitRepository, String branchName) throws GitAPIException {
        List<Ref> refs = gitRepository.branchList().call();

        for (Ref ref: refs) {
            if (ref.getName().equals(branchName)) {
                return ref;
            }
        }

        return null;
    }

    private Ref create(Git git, String branchName) throws GitAPIException {
        CreateBranchCommand branchCommand = git.branchCreate();
        branchCommand.setName(branchName);
        return branchCommand.call();
    }

	@Override
	public RevCommit commit(Git gitRepository, String message) throws GitAPIException {
		return gitRepository.commit().setMessage(message).call();
	}

	@Override
	public RevCommit commit(Git gitRepository, String message, PersonIdent personIdent) throws GitAPIException {
		return gitRepository.commit().setMessage(message).setAuthor(personIdent).call();
	}

}