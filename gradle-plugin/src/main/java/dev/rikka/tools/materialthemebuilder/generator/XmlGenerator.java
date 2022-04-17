package dev.rikka.tools.materialthemebuilder.generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public abstract class XmlGenerator {

    protected final File file;
    protected PrintStream os;

    public XmlGenerator(File file) {
        this.file = file;
    }

    protected abstract void startGenerate();

    protected abstract void onGenerate();

    protected abstract void endGenerate();

    public final void generate() throws IOException {
        createFile(file);
        os = new PrintStream(file);
        header();
        startGenerate();
        onGenerate();
        endGenerate();
        os.flush();
        os.close();
    }

    private void createFile(File file) throws IOException {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    throw new IOException("Failed to create " + file.getParentFile());
                }
            }
            if (!file.createNewFile()) {
                throw new IOException("Failed to create " + file);
            }
        }
    }

    protected final void header() {
        os.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    }
}
