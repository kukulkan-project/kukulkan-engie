/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2018 Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
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

package mx.infotec.dads.kukulkan.engine.service;

import static mx.infotec.dads.kukulkan.engine.service.FileUtil.copyFromJar;
import static mx.infotec.dads.kukulkan.engine.service.FileUtil.saveToFile;
import static mx.infotec.dads.kukulkan.engine.util.TemplateUtil.processString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import mx.infotec.dads.kukulkan.engine.templating.service.TemplateService;
import mx.infotec.dads.kukulkan.engine.util.FileUtils;
import mx.infotec.dads.kukulkan.engine.util.ListFileUtil;

/**
 * 
 * @author Roberto Villarejo Martínez <roberto.villarejo@infotec.mx>
 *
 */
@Service
public class WriterServiceImpl implements WriterService {

    private final Logger LOGGER = LoggerFactory.getLogger(WriterServiceImpl.class);

    private static final String ALL_FILES_PATTERN = ".*";

    private static final String TEMPLATES_DIR = "templates/";

    private static final String TEMPLATE_EXTENSION = ".ftl";

    @Autowired
    private TemplateService templateService;

    @Autowired
    private Configuration fmConfiguration;

    @Override
    public Optional<File> copyTemplate(String template, Path path, String relative, Object model) {
        String content = templateService.fillTemplate(template, model);
        Path toSave = path.resolve(processString(relative, model, fmConfiguration));
        if (saveToFile(toSave, content)) {
            return Optional.of(toSave.toFile());
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> copy(String resource, Path path, String relative) {
        Path toSave = path.resolve(relative);
        if (copyFromJar(TEMPLATES_DIR + resource, toSave)) {
            return Optional.of(toSave.toFile());
        }
        return Optional.empty();
    }

    @Override
    public void copyDir(Class clazz, String directory, Path path, String relative) {
        copyDir(clazz, directory, ALL_FILES_PATTERN, path, relative);
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, Path path, String relative) {
        List<String> files = ListFileUtil.listFiles(clazz, TEMPLATES_DIR + directory, pattern);
        for (String file : files) {
            copyFromJar(TEMPLATES_DIR + directory + "/" + file, path.resolve(relative).resolve(file));
        }
    }

    @Override
    public void copyDir(Class clazz, String directory, String pattern, Path path, String relative, Object model) {
        String processed = processString(relative, model, fmConfiguration);
        copyDir(clazz, directory, pattern, path, processed);
    }

    @Override
    public void copyDir(Class clazz, String directory, Path path, String relative, Object model) {
        copyDir(clazz, directory, ALL_FILES_PATTERN, path, relative, model);
    }

    @Override
    public Optional<File> copySmart(String resource, Path path, String toSave, Object model) {
        if (resource.endsWith(TEMPLATE_EXTENSION)) {
            return copyTemplate(resource, path, toSave, model);
        } else {
            return copy(resource, path, toSave, model);
        }
    }

    @Override
    public Optional<File> copy(String resource, Path path, String toSave, Object model) {
        return copy(resource, path, processString(toSave, model, fmConfiguration));
    }

    @Override
    public Optional<File> copyTemplate(String template, Path path, Function<String, String> function, Object model) {
        return copyTemplate(template, path, function.apply(template), model);
    }

    @Override
    public Optional<File> copy(String resource, Path path, Function<String, String> function) {
        return copy(resource, path, function.apply(resource));
    }

    @Override
    public Optional<File> copy(String resource, Path path, Function<String, String> function, Object model) {
        if (model == null) {
            return copy(resource, path, function.apply(resource));
        } else {
            return copy(resource, path, function.apply(resource), model);
        }
    }

    @Override
    public Optional<File> copySmart(String template, Path path, Function<String, String> function, Object model) {
        if (template.endsWith(TEMPLATE_EXTENSION)) {
            return copyTemplate(template, path, function, model);
        } else {
            return copy(template, path, function, model);
        }
    }

    @Override
    public Optional<File> save(Path path, String content) {
        if (FileUtil.saveToFile(path, content)) {
            return Optional.of(path.toFile());
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> rewriteFile(String template, Path file, Object model, String needle) {
        try {
            // Find needle in haystack
            List<String> haystack = Files.readAllLines(file);
            Optional<Integer> index = FileUtils.getNeedleIndex(haystack, needle);

            if (index.isPresent()) {
                // Fill template with model
                String filledTemplate = templateService.fillTemplate(template, model);
                // Avoid duplicated content insert
                if (haystack.stream().collect(Collectors.joining(System.lineSeparator())).contains(filledTemplate)) {
                    return Optional.empty();
                }
                List<String> content = Stream
                        .of(templateService.fillTemplate(template, model).split(System.lineSeparator()))
                        .collect(Collectors.toList());
                // Rewrite file
                haystack.addAll(index.get(), content);
                return save(file, haystack.stream().collect(Collectors.joining(System.lineSeparator())));
            }

        } catch (IOException e) {
            LOGGER.error("Failed to read {}", file);
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> addMavenDependency(String template, Path projectFolder) {
        return rewriteFile(template, projectFolder.resolve("pom.xml"), new Object(),
                "<!-- jhipster-needle-maven-add-dependency -->");
    }

    @Override
    public Optional<File> addEntityMenuEntry(String template, Path projectFolder, Object model) {
        return rewriteFile(template, projectFolder.resolve("src/main/webapp/app/layouts/navbar/navbar.html"), model,
                "<!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->");
    }

}
