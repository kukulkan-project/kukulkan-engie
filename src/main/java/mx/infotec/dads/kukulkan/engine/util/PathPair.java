package mx.infotec.dads.kukulkan.engine.util;

import java.nio.file.Path;

/**
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class PathPair {

    /** The real file path. */
    private Path realPath;

    /** The relative file path. */
    private Path relativePath;

    public PathPair(Path realPath, Path relativePath) {
        this.realPath = realPath;
        this.relativePath = relativePath;
    }

    public Path getRealPath() {
        return realPath;
    }

    public Path getRelativePath() {
        return relativePath;
    }

}
