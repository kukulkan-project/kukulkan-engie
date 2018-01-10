package mx.infotec.dads.kukulkan.engine.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import mx.infotec.dads.kukulkan.metamodel.util.BasePathEnum;

/**
 * PathBuilder. It is a fluent API for Create a file path into the generator.
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class PathBuilder {

    private PathBuilder() {

    }

    public static class Builder {
        Path sourcePath;

        public ProjectId outputDir(String outputDir) {
            sourcePath = Paths.get(outputDir);
            return new ProjectId(sourcePath);
        }

        public Path build() {
            return sourcePath;
        }

        public static class ProjectId {
            private Path pathHolder;

            public ProjectId(Path path) {
                pathHolder = path;
            }

            public BasePath projectId(String projectId) {
                return new BasePath(pathHolder.resolve(projectId));
            }
        }

        public static class BasePath {
            private Path pathHolder;

            public BasePath(Path path) {
                pathHolder = path;
            }

            public PackagingPath resourcePath(BasePathEnum basePath) {
                return new PackagingPath(pathHolder.resolve(basePath.getPath()));
            }

            public FolderPath folderName(String folderName) {
                return new FolderPath(pathHolder.resolve(folderName));
            }
        }

        public static class PackagingPath {
            private Path pathHolder;

            public PackagingPath(Path path) {
                pathHolder = path;
            }

            public FolderPath packaging(String packaging) {
                return new FolderPath(pathHolder.resolve(packaging));
            }
        }

        public static class FolderPath {
            private Path pathHolder;

            public FolderPath(Path path) {
                pathHolder = path;
            }

            public FolderPath folderName(String folderName) {
                pathHolder = pathHolder.resolve(folderName);
                return this;
            }

            public FilePath fileName(String fileName) {
                pathHolder = pathHolder.resolve(fileName);
                return new FilePath(pathHolder);
            }
        }

        public static class FilePath {
            private Path pathHolder;

            public FilePath(Path path) {
                pathHolder = path;
            }

            public Path build() {
                return pathHolder;
            }
        }

        public static class Packaging {
            private Path pathHolder;

            public Packaging(Path path) {
                pathHolder = path;
            }

            public BasePath packaging(String packaging) {
                return new BasePath(pathHolder.resolve(packaging));
            }
        }

        public Builder packaging(String packaging) {
            sourcePath = sourcePath.resolve(packaging);
            return this;
        }
    }
}
