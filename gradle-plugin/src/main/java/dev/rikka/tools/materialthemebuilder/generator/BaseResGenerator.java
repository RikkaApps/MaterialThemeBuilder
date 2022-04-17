package dev.rikka.tools.materialthemebuilder.generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public abstract class BaseResGenerator {

    private final File file;
    private PrintStream os;

    public BaseResGenerator(File file) {
        this.file = file;
    }

    public final void generate() throws IOException {
        createFile(file);
        os = new PrintStream(file);
        beginResource();
        onGenerate();
        endResource();
        os.flush();
        os.close();
    }

    protected abstract void onGenerate();

    protected final void createFile(File file) throws IOException {
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

    protected void beginResource() {
        os.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        os.println("<resources>");
    }

    protected void endResource() {
        os.println("</resources>");
    }

    protected void color(int color, String name) {
        os.printf("<color name=\"%s\">#%06X</color>%n", name, color);
    }

    protected void style(String name, String value) {
        os.printf("<item name=\"%s\">@color/%s</item>%n", name, value);
    }

    protected void attr(String name, String format) {
        os.printf("<attr name=\"%s\" format=\"%s\" />%n", name, format);
    }

    protected void beginStyle(String name, String parentName) {
        os.printf("<style name=\"%s\" parent=\"%s\">%n", name, parentName);
    }

    protected void endStyle() {
        os.println("</style>");
    }

    protected void beginDeclareStyleable(String name) {
        os.printf("<declare-styleable name=\"%s\">%n", name);
    }

    protected void endDeclareStyleable() {
        os.println("</declare-styleable>");
    }

    protected void print(String s) {
        os.print(s);
    }

}
