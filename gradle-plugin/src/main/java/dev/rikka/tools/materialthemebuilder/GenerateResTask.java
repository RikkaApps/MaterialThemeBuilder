package dev.rikka.tools.materialthemebuilder;

import dev.rikka.tools.materialthemebuilder.generator.ColorStateListGenerator;
import dev.rikka.tools.materialthemebuilder.generator.ValuesAllGenerator;
import dev.rikka.tools.materialthemebuilder.generator.ValuesV31Generator;
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
import java.util.ArrayList;
import java.util.List;

public class GenerateResTask extends DefaultTask {

    private final File dir;
    private final ValuesAllGenerator valuesAllGenerator;
    private final ValuesV31Generator valuesV31Generator;
    private final List<ColorStateListGenerator> colorStateListGenerators = new ArrayList<>();

    @Inject
    public GenerateResTask(MaterialThemeBuilderExtension extension, File dir) {
        this.dir = dir;

        if (extension.isGenerateTextColors()) {
            for (String textColor : MaterialTheme.TEXT_COLORS) {
                for (String emphasis : MaterialTheme.TEXT_COLOR_EMPHASIS) {
                    String filename = "color/"
                            + MaterialTheme.getColorStateListFilename(textColor, emphasis)
                            + ".xml";
                    colorStateListGenerators.add(
                            new ColorStateListGenerator(new File(dir, filename), "?" + textColor, emphasis));
                }
            }
        }

        valuesAllGenerator = new ValuesAllGenerator(new File(dir, "values/values.xml"), extension);
        valuesV31Generator = new ValuesV31Generator(new File(dir, "values-v31/values.xml"), extension);
    }

    @TaskAction
    public void generate() throws IOException {
        Util.clearDir(dir);
        for (ColorStateListGenerator colorStateListGenerator : colorStateListGenerators) {
            colorStateListGenerator.generate();
        }
        valuesAllGenerator.generate();
        valuesV31Generator.generate();
    }


}

