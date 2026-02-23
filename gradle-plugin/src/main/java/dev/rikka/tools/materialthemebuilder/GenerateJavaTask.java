package dev.rikka.tools.materialthemebuilder;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.io.PrintStream;

public abstract class GenerateJavaTask extends DefaultTask {

    private static final String CLASSNAME = "Harmonization";

    @Input
    @Optional
    public abstract Property<String> getPackageName();

    @Input
    public abstract ListProperty<String> getHarmonizedAttrs();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @TaskAction
    public void generate() throws IOException {
        Util.clearDir(getOutputDir().get().getAsFile());

        if (!getPackageName().isPresent()) {
            return;
        }

        var file = getOutputDir().file(String.format("%s.java",
                String.join("/", (getPackageName().get() + "." + CLASSNAME).split("\\.")))).get().getAsFile();
        Util.createFile(file);

        var os = new PrintStream(file);
        write(os);
        os.flush();
        os.close();
    }

    private void write(PrintStream os) {
        String content = """
                package %s;
                
                public final class %s {
                    public static final int[] HARMONIZED_COLOR_ATTRIBUTES = {%s};
                }
                """;

        os.printf(content,
                getPackageName().get(),
                CLASSNAME,
                String.join(", ", getHarmonizedAttrs().get().toArray(new String[0])));
    }
}
