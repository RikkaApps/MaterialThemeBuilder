package dev.rikka.tools.materialthemebuilder.generator;

import dev.rikka.tools.materialthemebuilder.Util;

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
        Util.createFile(file);
        os = new PrintStream(file);
        header();
        startGenerate();
        onGenerate();
        endGenerate();
        os.flush();
        os.close();
    }

    protected final void header() {
        os.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    }
}
