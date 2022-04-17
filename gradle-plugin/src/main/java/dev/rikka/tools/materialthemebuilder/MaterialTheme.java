package dev.rikka.tools.materialthemebuilder;

import com.google.common.base.CaseFormat;

public class MaterialTheme {

    public static final int[] TONE_VALUES = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95, 99, 100};

    public static final String[] COLORS = new String[]{
            "colorPrimary",
            "colorOnPrimary",
            "colorPrimaryContainer",
            "colorOnPrimaryContainer",
            "colorSecondary",
            "colorOnSecondary",
            "colorSecondaryContainer",
            "colorOnSecondaryContainer",
            "colorTertiary",
            "colorOnTertiary",
            "colorTertiaryContainer",
            "colorOnTertiaryContainer",
            "colorError",
            "colorOnError",
            "colorErrorContainer",
            "colorOnErrorContainer",
            "colorBackground",
            "colorOnBackground",
            "colorSurface",
            "colorOnSurface",
            "colorOutline",
            "colorSurfaceVariant",
            "colorOnSurfaceVariant"
    };

    public static final String[] TEXT_COLORS = new String[]{
            "colorOnPrimary",
            "colorOnPrimaryContainer",
            "colorOnSecondary",
            "colorOnSecondaryContainer",
            "colorOnTertiary",
            "colorOnTertiaryContainer",
            "colorOnError",
            "colorOnErrorContainer",
            "colorOnBackground",
            "colorOnSurface",
            "colorOnSurfaceVariant"
    };

    public static final String[] TEXT_COLOR_EMPHASIS = new String[]{
            "", "High", "Medium"
    };

    public static String getColorStateListFilename(String color, String emphasis) {
        return "m3_text_"
                + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, color)
                + ("".equals(emphasis) ? "" : "_")
                + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, emphasis)
                + ("".equals(emphasis) ? "" : "_emphasis");
    }
}
