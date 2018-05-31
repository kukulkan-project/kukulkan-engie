package mx.infotec.dads.kukulkan.engine.service;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

public interface GitProcessorService {

    /**
     * Init a git repository.
     * 
     * @param pathname The path where will be init the repository.
     * @return A git repository.
     */
    Git init(String pathname) throws GitProcessorException, GitAPIException, IOException;

    /**
     * Init a git repository.
     * 
     * @param directory The file directory where will be init the repository.
     * @return A git repository.
     */
    Git init(File directory) throws GitProcessorException, GitAPIException, IOException;

    DirCache addFiles(Git gitRepository, String... filePaths) throws GitProcessorException, GitAPIException;

    Ref createBranch(Git gitRepository, String branchName) throws GitAPIException;

    Ref switchBranch(Git gitRepository, String branchName) throws GitAPIException;

    RevCommit commit(Git gitRepository, String message) throws GitAPIException;

    RevCommit commit(Git gitRepository, String message, PersonIdent personIdent) throws GitAPIException;

    /**
     * A general exception.
     */
    public class GitProcessorException extends Exception{

        private static final long serialVersionUID = 1L;

		public GitProcessorException() {
            super();
        }

        public GitProcessorException(String message) {
            super(message);
        }
    }

}