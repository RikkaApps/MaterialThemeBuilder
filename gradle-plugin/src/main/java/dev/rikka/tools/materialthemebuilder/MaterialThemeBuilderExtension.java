package dev.rikka.tools.materialthemebuilder;

import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public abstract class MaterialThemeBuilderExtension {

    public abstract ListProperty<Theme> getThemes();

    public abstract Property<Boolean> getGeneratePalette();

    public MaterialThemeBuilderExtension() {
        getGeneratePalette().set(true);

        ((ExtensionAware) this).getExtensions().create("theme", Theme.class);
    }

    public abstract static class Theme {

        public abstract Property<String> getName();

        public abstract Property<String> getPrimaryColor();

        public abstract Property<String> getSecondaryColor();

        public abstract Property<String> getTertiaryColor();

        public abstract Property<String> getNeutralColor();

        public abstract Property<String> getLightThemeFormat();

        public abstract Property<String> getDarkThemeFormat();

        public abstract Property<String> getLightThemeParent();

        public abstract Property<String> getDarkThemeParent();

        public Theme() {
        }

        // TODO Extended colors

    }
}
