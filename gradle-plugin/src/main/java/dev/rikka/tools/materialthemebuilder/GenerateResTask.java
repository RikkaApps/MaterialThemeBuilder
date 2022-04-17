package dev.rikka.tools.materialthemebuilder;

import dev.rikka.tools.materialthemebuilder.generator.ResGenerator;
import dev.rikka.tools.materialthemebuilder.generator.ResV31Generator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class GenerateResTask extends DefaultTask {

    private final File dir;
    private final ResGenerator resGenerator;
    private final ResV31Generator resV31Generator;

    @Inject
    public GenerateResTask(MaterialThemeBuilderExtension extension, File dir) {
        this.dir = dir;

        resGenerator = new ResGenerator(new File(dir, "values/values.xml"), extension);
        resV31Generator = new ResV31Generator(new File(dir, "values-v31/values.xml"), extension);
    }

    @TaskAction
    public void generate() throws IOException {
        try {
            Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Throwable ignored) {
        }

        resGenerator.generate();
        resV31Generator.generate();
    }
}

