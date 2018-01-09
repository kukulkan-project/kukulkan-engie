package mx.infotec.dads.kukulkan.engine.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import mx.infotec.dads.kukulkan.metamodel.util.BasePathEnum;

/**
 * PathBuilder
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class PathBuilder {

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

            public BasePath basePath(BasePathEnum basePath) {
                return new BasePath(pathHolder.resolve(basePath.getPath()));
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

    public static void main(String[] args) {
        Builder pb = new PathBuilder.Builder();
        Path path = pb.outputDir("/home/daniel").projectId(projectId)
    }
}
