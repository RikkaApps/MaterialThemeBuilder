package dev.rikka.tools.materialthemebuilder;

import groovy.lang.Closure;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.List;

public abstract class MaterialThemeBuilderExtension {

    private final Project project;

    private final List<Theme> themes = new ArrayList<>();

    public boolean generatePalette = false;

    public MaterialThemeBuilderExtension(Project project) {
        this.project = project;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    private Theme configureAndAdd(Theme theme, Closure<Theme> configure) {
        project.configure(theme, configure);
        themes.add(theme);
        return theme;
    }

    public Theme theme(String name, Closure<Theme> configure) {
        return configureAndAdd(new Theme(name), configure);
    }

    public boolean isGeneratePalette() {
        return generatePalette;
    }

    public void setGeneratePalette(boolean generatePalette) {
        this.generatePalette = generatePalette;
    }

    public static class Theme {

        private String name;
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

        public void setName(String name) {
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
