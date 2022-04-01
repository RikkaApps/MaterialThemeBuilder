package dev.rikka.tools.materialthemebuilder;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;

public abstract class MaterialThemeBuilderExtension {
    public boolean generatePalette = false;

    public abstract NamedDomainObjectContainer<Theme> getThemes();

    public boolean isGeneratePalette() {
        return generatePalette;
    }

    public void setGeneratePalette(boolean generatePalette) {
        this.generatePalette = generatePalette;
    }

    public void themes(Action<NamedDomainObjectContainer<Theme>> container) {
        container.execute(getThemes());
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

        // TODO Extended colors

    }
}
