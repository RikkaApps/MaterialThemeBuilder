package dev.rikka.tools.materialthemebuilder;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.List;

public class MaterialTheme {

    public static final int[] TONE_VALUES = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95, 99, 100};

    private static final int TONE_ACCENT_LIGHT = 40;
    private static final int TONE_ON_ACCENT_LIGHT = 100;
    private static final int TONE_ACCENT_CONTAINER_LIGHT = 90;
    private static final int TONE_ON_ACCENT_CONTAINER_LIGHT = 10;
    private static final int TONE_ACCENT_DARK = 70;
    private static final int TONE_ON_ACCENT_DARK = 10;
    private static final int TONE_ACCENT_CONTAINER_DARK = 20;
    private static final int TONE_ON_ACCENT_CONTAINER_DARK = 80;

    private static final String[] COLOR_TYPES = new String[]{
            "Primary", "Secondary", "Tertiary", "Error"
    };

    private static final String[] ATTRIBUTE_NAME_FORMAT = new String[]{
            "color%s",
            "colorOn%s",
            "color%sContainer",
            "colorOn%sContainer"
    };

    private static final String[] NAME_FORMAT = new String[]{
            "%s",
            "on%s",
            "%sContainer",
            "on%sContainer"
    };

    private static final int[] TONE_LIGHT = new int[]{
            TONE_ACCENT_LIGHT,
            TONE_ON_ACCENT_LIGHT,
            TONE_ACCENT_CONTAINER_LIGHT,
            TONE_ON_ACCENT_CONTAINER_LIGHT
    };

    private static final int[] TONE_DARK = new int[]{
            TONE_ACCENT_DARK,
            TONE_ON_ACCENT_DARK,
            TONE_ACCENT_CONTAINER_DARK,
            TONE_ON_ACCENT_CONTAINER_DARK
    };

    /*public static final String[] COLORS = new String[]{
            "colorBackground",
            "colorOnBackground",
            "colorSurface",
            "colorOnSurface",
            "colorOutline",
            "colorSurfaceVariant",
            "colorOnSurfaceVariant"
    };*/

    public static class Color {

        private final String nameFormat;
        private final String attributeNameFormat;
        private final int toneLight;
        private final int toneDark;

        public Color(String nameFormat, String attributeNameFormat, int toneLight, int toneDark) {
            this.nameFormat = nameFormat;
            this.attributeNameFormat = attributeNameFormat;
            this.toneLight = toneLight;
            this.toneDark = toneDark;
        }

        public String getAttributeName(String name) {
            return String.format(attributeNameFormat, name);
        }

        public String getName(String name) {
            return String.format(nameFormat, name);
        }

        public String getFileName(String themeName, boolean isLight, String colorName) {
            // md_theme_<theme name>_<light/dark>_<color name, e.g., colorPrimary>
            return String.format("md_theme_%s_%s_%s",
                    themeName,
                    isLight ?"light" : "dark",
                    CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getName(colorName)));
        }

        public int getToneLight() {
            return toneLight;
        }

        public int getToneDark() {
            return toneDark;
        }
    }

    public static final List<Color> COLORS = new ArrayList<>();

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

    static {
        for (int i = 0; i < ATTRIBUTE_NAME_FORMAT.length; i++) {
            COLORS.add(new Color(
                    NAME_FORMAT[i],
                    ATTRIBUTE_NAME_FORMAT[i],
                    TONE_LIGHT[i],
                    TONE_DARK[i]
            ));
        }
    }

    public static String getColorStateListFilename(String color, String emphasis) {
        return "m3_text_"
                + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, color)
                + ("".equals(emphasis) ? "" : "_")
                + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, emphasis)
                + ("".equals(emphasis) ? "" : "_emphasis");
    }

}
