package dev.rikka.tools.materialthemebuilder;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GenerateJavaTask extends DefaultTask {

    private static final String CLASSNAME = "Harmonization";

    private final MaterialThemeBuilderExtension extension;
    private final File dir;
    private final File file;

    @Inject
    public GenerateJavaTask(MaterialThemeBuilderExtension extension, File dir) {
        this.extension = extension;
        this.dir = dir;
        this.file = new File(dir, String.format("%s.java",
                String.join("/", (extension.getPackageName() + "." + CLASSNAME).split("\\."))));
    }

    @TaskAction
    public void generate() throws IOException {
        Util.clearDir(dir);

        if (extension.getPackageName() == null) {
            return;
        }

        Util.createFile(file);

        var os = new PrintStream(file);
        write(os);
        os.flush();
        os.close();
    }

    public void write(PrintStream os) {
        String content = "package %s;\n" +
                "\n" +
                "public final class %s {\n" +
                "    public static final int[] HARMONIZED_COLOR_ATTRIBUTES = {%s};\n" +
                "}\n";

        List<String> attrs = new ArrayList<>();

        for (MaterialThemeBuilderExtension.ExtendedColor extendedColor : extension.getExtendedColors()) {
            if (!extendedColor.isHarmonize()) {
                continue;
            }

            for (MaterialTheme.Color color : MaterialTheme.COLORS) {
                attrs.add("R.attr." + color.getAttributeName(extendedColor.getNameForAttribute()));
            }
        }

        os.printf(content,
                extension.getPackageName(),
                CLASSNAME,
                String.join(", ", attrs.toArray(new String[0])));
    }
}
