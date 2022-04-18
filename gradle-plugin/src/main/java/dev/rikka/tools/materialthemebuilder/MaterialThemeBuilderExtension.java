package dev.rikka.tools.materialthemebuilder;

import com.google.common.base.CaseFormat;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;

import java.util.Locale;

public abstract class MaterialThemeBuilderExtension {

    public boolean generatePalette = false;

    public boolean generatePaletteAttributes = false;

    public boolean generateTextColors = false;

    public abstract NamedDomainObjectContainer<Theme> getThemes();

    public abstract NamedDomainObjectContainer<ExtendedColor> getExtendedColors();

    public boolean isGeneratePalette() {
        return generatePalette;
    }

    public void setGeneratePalette(boolean generatePalette) {
        this.generatePalette = generatePalette;
    }

    public boolean isGeneratePaletteAttributes() {
        return generatePaletteAttributes;
    }

    public void setGeneratePaletteAttributes(boolean generatePaletteAttributes) {
        this.generatePaletteAttributes = generatePaletteAttributes;
    }

    public boolean isGenerateTextColors() {
        return generateTextColors;
    }

    public void setGenerateTextColors(boolean generateTextColors) {
        this.generateTextColors = generateTextColors;
    }

    public void themes(Action<NamedDomainObjectContainer<Theme>> container) {
        container.execute(getThemes());
    }

    public void extendedColors(Action<NamedDomainObjectContainer<ExtendedColor>> container) {
        container.execute(getExtendedColors());
    }

    public static class Theme {
        private final String name;
        private String primaryColor;
        private String secondaryColor;
        private String tertiaryColor;
        private String neutralColor;
        private String lightThemeFormat;
        private String lightThemeParent;
        private String darkThemeFormat;
        private String darkThemeParent;
        private boolean isDynamicColors;

        public Theme(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getPrimaryColor() {
            return primaryColor;
        }

        public void setPrimaryColor(String primaryColor) {
            this.primaryColor = primaryColor;
        }

        public String getSecondaryColor() {
            return secondaryColor;
        }

        public void setSecondaryColor(String secondaryColor) {
            this.secondaryColor = secondaryColor;
        }

        public String getTertiaryColor() {
            return tertiaryColor;
        }

        public void setTertiaryColor(String tertiaryColor) {
            this.tertiaryColor = tertiaryColor;
        }

        public String getNeutralColor() {
            return neutralColor;
        }

        public void setNeutralColor(String neutralColor) {
            this.neutralColor = neutralColor;
        }

        public String getLightThemeFormat() {
            return lightThemeFormat;
        }

        public void setLightThemeFormat(String lightThemeFormat) {
            this.lightThemeFormat = lightThemeFormat;
        }

        public String getLightThemeParent() {
            return lightThemeParent;
        }

        public void setLightThemeParent(String lightThemeParent) {
            this.lightThemeParent = lightThemeParent;
        }

        public String getDarkThemeFormat() {
            return darkThemeFormat;
        }

        public void setDarkThemeFormat(String darkThemeFormat) {
            this.darkThemeFormat = darkThemeFormat;
        }

        public String getDarkThemeParent() {
            return darkThemeParent;
        }

        public void setDarkThemeParent(String darkThemeParent) {
            this.darkThemeParent = darkThemeParent;
        }

        public boolean isDynamicColors() {
            return isDynamicColors;
        }

        public void setDynamicColors(boolean dynamicColors) {
            isDynamicColors = dynamicColors;
        }
    }

    public static class ExtendedColor {

        private final String name;
        private String color;
        private boolean harmonize;

        public ExtendedColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getNameForAttribute() {
            var nameIsUnderScore = name.contains("_");
            if (nameIsUnderScore) {
                return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name.toLowerCase(Locale.ROOT));
            } else {
                return Util.capitalize(name);
            }
        }

        public String getNameForFile() {
            var nameIsUnderScore = name.contains("_");
            if (nameIsUnderScore) {
                return name.toLowerCase(Locale.ROOT);
            } else {
                return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, Util.capitalize(name));
            }
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public boolean isHarmonize() {
            return harmonize;
        }

        public void setHarmonize(boolean harmonize) {
            this.harmonize = harmonize;
        }
    }
}
