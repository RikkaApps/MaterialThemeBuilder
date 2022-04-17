package dev.rikka.tools.materialthemebuilder.generator;

import java.io.File;

public class ColorStateListGenerator extends XmlGenerator {

    private final String emphasis;
    private final String color;

    public ColorStateListGenerator(File file, String color, String emphasis) {
        super(file);
        this.emphasis = emphasis;
        this.color = color;
    }

    @Override
    protected void startGenerate() {
        os.println("<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">");
    }

    @Override
    protected void onGenerate() {
        os.print("    ");
        os.printf("<item android:alpha=\"%2$s\" android:color=\"%1$s\" android:state_enabled=\"false\" />%n", color, getAlpha(true));
        os.print("    ");
        if ("".equals(emphasis)) {
            os.printf("<item android:color=\"%1$s\" />%n", color);
        } else {
            os.printf("<item android:alpha=\"%2$s\" android:color=\"%1$s\" />%n", color, getAlpha(false));

        }
    }

    @Override
    protected void endGenerate() {
        os.println("</selector>");
    }

    private String getAlpha(boolean isDisabled) {
        if (!isDisabled) {
            switch (emphasis) {
                case "High":
                    return "@dimen/m3_emphasis_high";
                case "Medium":
                    return "@dimen/m3_emphasis_medium";
                default:
                    return "";
            }
        } else {
            switch (emphasis) {
                case "High":
                    return "@dimen/m3_emphasis_high_disabled";
                case "Medium":
                    return "@dimen/m3_emphasis_medium_disabled";
                default:
                    return "";
            }
        }
    }
}
