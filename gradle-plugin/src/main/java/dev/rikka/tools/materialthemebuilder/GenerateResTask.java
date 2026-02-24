package dev.rikka.tools.materialthemebuilder;

import dev.rikka.tools.materialthemebuilder.generator.ColorStateListGenerator;
import dev.rikka.tools.materialthemebuilder.generator.ValuesAllGenerator;
import dev.rikka.tools.materialthemebuilder.generator.ValuesV31Generator;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public abstract class GenerateResTask extends DefaultTask {
    @Internal
    public abstract Property<MaterialThemeBuilderExtension> getExtension();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @TaskAction
    public void generate() throws IOException {
        var extension = getExtension().get();

        Util.clearDir(getOutputDir().get().getAsFile());
        if (extension.isGenerateTextColors()) {
            for (String textColor : MaterialTheme.TEXT_COLORS) {
                for (String emphasis : MaterialTheme.TEXT_COLOR_EMPHASIS) {
                    String filename = "color/"
                            + MaterialTheme.getColorStateListFilename(textColor, emphasis)
                            + ".xml";
                    new ColorStateListGenerator(
                            getOutputDir().file(filename).get().getAsFile(),
                            "?" + textColor,
                            emphasis
                    ).generate();
                }
            }
        }

        new ValuesAllGenerator(getOutputDir().file("values/values.xml").get().getAsFile(), extension).generate();
        new ValuesV31Generator(getOutputDir().file("values-v31/values.xml").get().getAsFile(), extension).generate();
    }
}
