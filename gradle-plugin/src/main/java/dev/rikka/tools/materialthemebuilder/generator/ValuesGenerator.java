package dev.rikka.tools.materialthemebuilder.generator;

import com.google.common.base.CaseFormat;
import dev.rikka.tools.materialthemebuilder.MaterialTheme;

import java.io.File;

public abstract class ValuesGenerator extends XmlGenerator {

    public ValuesGenerator(File file) {
        super(file);
    }

    @Override
    protected void startGenerate() {
        beginResource();
    }

    @Override
    protected void endGenerate() {
        endResource();
    }

    protected void beginResource() {
        os.println("<resources>");
    }

    protected void endResource() {
        os.println("</resources>");
    }

    protected void color(int color, String name) {
        os.printf("<color name=\"%s\">#%06X</color>%n", name, color);
    }

    protected void styleColorRef(String name, String value) {
        os.printf("<item name=\"%s\">@color/%s</item>%n", name, value);
    }

    protected void style(String name, String value) {
        os.printf("<item name=\"%s\">%s</item>%n", name, value);
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

    protected void textColorStyles() {
        for (String textColor : MaterialTheme.TEXT_COLORS) {
            for (String emphasis : MaterialTheme.TEXT_COLOR_EMPHASIS) {
                String name = "text"
                        + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, textColor)
                        + emphasis
                        + ("".equals(emphasis) ? "" : "Emphasis");
                String value = MaterialTheme.getColorStateListFilename(textColor, emphasis);
                styleColorRef(name, value);
            }
        }
    }
}
