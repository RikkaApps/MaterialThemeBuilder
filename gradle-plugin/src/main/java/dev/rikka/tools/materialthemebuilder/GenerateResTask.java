package dev.rikka.tools.materialthemebuilder;

import palettes.CorePalette;
import palettes.TonalPalette;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class GenerateResTask extends GenerateTask {

    private static final int[] TONE_VALUES = new int[]{100, 99, 95, 90, 80, 70, 60, 50, 40, 30, 20, 10, 0};

    private final MaterialThemeBuilderExtension extension;
    private final File file;

    private PrintStream os;

    @Inject
    public GenerateResTask(MaterialThemeBuilderExtension extension, File dir) {
        super(dir);

        this.extension = extension;
        this.file = new File(dir, "values/values.xml");
    }

    @Override
    public void generate() throws IOException {
        super.generate();

        createFile(file);

        os = new PrintStream(file);
        write();
        os.flush();
        os.close();
    }

    private void beginResource() {
        os.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        os.println("<resources>");
    }

    private void endResource() {
        os.println("</resources>");
    }

    private void color(int color, String name) {
        os.printf("<color name=\"%s\">#%06X</color>%n", name, color);
    }

    private void style(String name, String value) {
        os.printf("<item name=\"%s\">@color/%s</item>%n", name, value);
    }

    private void beginStyle(String name, String parentName) {
        os.printf("<style name=\"%s\" parent=\"%s\">%n", name, parentName);
    }

    private void endStyle() {
        os.println("</style>");
    }

    private void writeColorsForTheme(
            TonalPalette primary, TonalPalette secondary, TonalPalette tertiary, TonalPalette error,
            TonalPalette neutral, TonalPalette neutralVariant, String name) {

        var nameDecapitalized = Util.decapitalize(name);

        // MDC light colors
        color(primary.tone(40), String.format("md_theme_%s_light_primary", nameDecapitalized));
        color(primary.tone(100), String.format("md_theme_%s_light_onPrimary", nameDecapitalized));
        color(primary.tone(90), String.format("md_theme_%s_light_primaryContainer", nameDecapitalized));
        color(primary.tone(10), String.format("md_theme_%s_light_onPrimaryContainer", nameDecapitalized));

        color(secondary.tone(40), String.format("md_theme_%s_light_secondary", nameDecapitalized));
        color(secondary.tone(100), String.format("md_theme_%s_light_onSecondary", nameDecapitalized));
        color(secondary.tone(90), String.format("md_theme_%s_light_secondaryContainer", nameDecapitalized));
        color(secondary.tone(10), String.format("md_theme_%s_light_onSecondaryContainer", nameDecapitalized));

        color(tertiary.tone(40), String.format("md_theme_%s_light_tertiary", nameDecapitalized));
        color(tertiary.tone(100), String.format("md_theme_%s_light_onTertiary", nameDecapitalized));
        color(tertiary.tone(90), String.format("md_theme_%s_light_tertiaryContainer", nameDecapitalized));
        color(tertiary.tone(10), String.format("md_theme_%s_light_onTertiaryContainer", nameDecapitalized));

        color(error.tone(40), String.format("md_theme_%s_light_error", nameDecapitalized));
        color(error.tone(100), String.format("md_theme_%s_light_errorContainer", nameDecapitalized));
        color(error.tone(90), String.format("md_theme_%s_light_onError", nameDecapitalized));
        color(error.tone(10), String.format("md_theme_%s_light_onErrorContainer", nameDecapitalized));

        color(neutral.tone(99), String.format("md_theme_%s_light_background", nameDecapitalized));
        color(neutral.tone(10), String.format("md_theme_%s_light_onBackground", nameDecapitalized));
        color(neutral.tone(99), String.format("md_theme_%s_light_surface", nameDecapitalized));
        color(neutral.tone(10), String.format("md_theme_%s_light_onSurface", nameDecapitalized));

        color(neutralVariant.tone(90), String.format("md_theme_%s_light_surfaceVariant", nameDecapitalized));
        color(neutralVariant.tone(30), String.format("md_theme_%s_light_onSurfaceVariant", nameDecapitalized));
        color(neutralVariant.tone(50), String.format("md_theme_%s_light_outline", nameDecapitalized));

        color(neutral.tone(95), String.format("md_theme_%s_light_inverseOnSurface", nameDecapitalized));
        color(neutral.tone(20), String.format("md_theme_%s_light_inverseSurface", nameDecapitalized));
        color(primary.tone(80), String.format("md_theme_%s_light_primaryInverse", nameDecapitalized));

        // MDC dark colors
        color(primary.tone(80), String.format("md_theme_%s_dark_primary", nameDecapitalized));
        color(primary.tone(20), String.format("md_theme_%s_dark_onPrimary", nameDecapitalized));
        color(primary.tone(30), String.format("md_theme_%s_dark_primaryContainer", nameDecapitalized));
        color(primary.tone(90), String.format("md_theme_%s_dark_onPrimaryContainer", nameDecapitalized));

        color(secondary.tone(80), String.format("md_theme_%s_dark_secondary", nameDecapitalized));
        color(secondary.tone(20), String.format("md_theme_%s_dark_onSecondary", nameDecapitalized));
        color(secondary.tone(30), String.format("md_theme_%s_dark_secondaryContainer", nameDecapitalized));
        color(secondary.tone(90), String.format("md_theme_%s_dark_onSecondaryContainer", nameDecapitalized));

        color(tertiary.tone(80), String.format("md_theme_%s_dark_tertiary", nameDecapitalized));
        color(tertiary.tone(20), String.format("md_theme_%s_dark_onTertiary", nameDecapitalized));
        color(tertiary.tone(30), String.format("md_theme_%s_dark_tertiaryContainer", nameDecapitalized));
        color(tertiary.tone(90), String.format("md_theme_%s_dark_onTertiaryContainer", nameDecapitalized));

        color(error.tone(80), String.format("md_theme_%s_dark_error", nameDecapitalized));
        color(error.tone(20), String.format("md_theme_%s_dark_errorContainer", nameDecapitalized));
        color(error.tone(30), String.format("md_theme_%s_dark_onError", nameDecapitalized));
        color(error.tone(90), String.format("md_theme_%s_dark_onErrorContainer", nameDecapitalized));

        color(neutral.tone(10), String.format("md_theme_%s_dark_background", nameDecapitalized));
        color(neutral.tone(90), String.format("md_theme_%s_dark_onBackground", nameDecapitalized));
        color(neutral.tone(10), String.format("md_theme_%s_dark_surface", nameDecapitalized));
        color(neutral.tone(80), String.format("md_theme_%s_dark_onSurface", nameDecapitalized));

        color(neutralVariant.tone(30), String.format("md_theme_%s_dark_surfaceVariant", nameDecapitalized));
        color(neutralVariant.tone(80), String.format("md_theme_%s_dark_onSurfaceVariant", nameDecapitalized));
        color(neutralVariant.tone(60), String.format("md_theme_%s_dark_outline", nameDecapitalized));

        color(neutral.tone(40), String.format("md_theme_%s_dark_inverseOnSurface", nameDecapitalized));
        color(neutral.tone(90), String.format("md_theme_%s_dark_inverseSurface", nameDecapitalized));
        color(primary.tone(40), String.format("md_theme_%s_dark_primaryInverse", nameDecapitalized));

        // MD3 color tokens
        if (extension.getGeneratePalette().get()) {
            for (int tone : TONE_VALUES) {
                color(primary.tone(tone), String.format("md_theme_%s_palette_primary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                color(secondary.tone(tone), String.format("md_theme_%s_palette_secondary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                color(tertiary.tone(tone), String.format("md_theme_%s_palette_tertiary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                color(error.tone(tone), String.format("md_theme_%s_palette_error_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                color(neutral.tone(tone), String.format("md_theme_%s_palette_neutral_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                color(neutralVariant.tone(tone), String.format("md_theme_%s_palette_neutral_variant_%d", nameDecapitalized, tone));
            }
        }
    }

    private void writeStylesForTheme(
            String name,
            String lightThemeNameFormat, String parentLightThemeName,
            String darkThemeNameFormat, String parentDarkThemeName) {

        var nameCapitalized = Util.capitalize(name);
        var nameDecapitalized = Util.decapitalize(name);

        beginStyle(String.format(lightThemeNameFormat, nameCapitalized), parentLightThemeName);
        style("colorPrimary", String.format("md_theme_%s_light_primary", nameDecapitalized));
        style("colorOnPrimary", String.format("md_theme_%s_light_onPrimary", nameDecapitalized));
        style("colorPrimaryContainer", String.format("md_theme_%s_light_primaryContainer", nameDecapitalized));
        style("colorOnPrimaryContainer", String.format("md_theme_%s_light_onPrimaryContainer", nameDecapitalized));
        style("colorSecondary", String.format("md_theme_%s_light_secondary", nameDecapitalized));
        style("colorOnSecondary", String.format("md_theme_%s_light_onSecondary", nameDecapitalized));
        style("colorSecondaryContainer", String.format("md_theme_%s_light_secondaryContainer", nameDecapitalized));
        style("colorOnSecondaryContainer", String.format("md_theme_%s_light_onSecondaryContainer", nameDecapitalized));
        style("colorTertiary", String.format("md_theme_%s_light_tertiary", nameDecapitalized));
        style("colorOnTertiary", String.format("md_theme_%s_light_onTertiary", nameDecapitalized));
        style("colorTertiaryContainer", String.format("md_theme_%s_light_tertiaryContainer", nameDecapitalized));
        style("colorOnTertiaryContainer", String.format("md_theme_%s_light_onTertiaryContainer", nameDecapitalized));
        style("colorError", String.format("md_theme_%s_light_error", nameDecapitalized));
        style("colorErrorContainer", String.format("md_theme_%s_light_errorContainer", nameDecapitalized));
        style("colorOnError", String.format("md_theme_%s_light_onError", nameDecapitalized));
        style("colorOnErrorContainer", String.format("md_theme_%s_light_onErrorContainer", nameDecapitalized));
        style("android:colorBackground", String.format("md_theme_%s_light_surface", nameDecapitalized));
        style("colorOnBackground", String.format("md_theme_%s_light_onBackground", nameDecapitalized));
        style("colorSurface", String.format("md_theme_%s_light_surface", nameDecapitalized));
        style("colorOnSurface", String.format("md_theme_%s_light_onSurface", nameDecapitalized));
        style("colorSurfaceVariant", String.format("md_theme_%s_light_surfaceVariant", nameDecapitalized));
        style("colorOnSurfaceVariant", String.format("md_theme_%s_light_onSurfaceVariant", nameDecapitalized));
        style("colorOutline", String.format("md_theme_%s_light_outline", nameDecapitalized));
        style("colorOnSurfaceInverse", String.format("md_theme_%s_light_inverseOnSurface", nameDecapitalized));
        style("colorSurfaceInverse", String.format("md_theme_%s_light_inverseSurface", nameDecapitalized));
        style("colorPrimaryInverse", String.format("md_theme_%s_light_primaryInverse", nameDecapitalized));

        if (extension.isGeneratePalette()) {
            for (int tone : TONE_VALUES) {
                style(String.format("palettePrimary%d", tone),
                        String.format("md_theme_%s_palette_primary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteSecondary%d", tone),
                        String.format("md_theme_%s_palette_secondary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteTertiary%d", tone),
                        String.format("md_theme_%s_palette_tertiary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteError%d", tone),
                        String.format("md_theme_%s_palette_error_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteNeutral%d", tone),
                        String.format("md_theme_%s_palette_neutral_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteNeutralVariant%d", tone),
                        String.format("md_theme_%s_palette_neutral_variant_%d", nameDecapitalized, tone));
            }
        }
        endStyle();

        beginStyle(String.format(darkThemeNameFormat, nameCapitalized), parentDarkThemeName);
        style("colorPrimary", String.format("md_theme_%s_dark_primary", nameDecapitalized));
        style("colorOnPrimary", String.format("md_theme_%s_dark_onPrimary", nameDecapitalized));
        style("colorPrimaryContainer", String.format("md_theme_%s_dark_primaryContainer", nameDecapitalized));
        style("colorOnPrimaryContainer", String.format("md_theme_%s_dark_onPrimaryContainer", nameDecapitalized));
        style("colorSecondary", String.format("md_theme_%s_dark_secondary", nameDecapitalized));
        style("colorOnSecondary", String.format("md_theme_%s_dark_onSecondary", nameDecapitalized));
        style("colorSecondaryContainer", String.format("md_theme_%s_dark_secondaryContainer", nameDecapitalized));
        style("colorOnSecondaryContainer", String.format("md_theme_%s_dark_onSecondaryContainer", nameDecapitalized));
        style("colorTertiary", String.format("md_theme_%s_dark_tertiary", nameDecapitalized));
        style("colorOnTertiary", String.format("md_theme_%s_dark_onTertiary", nameDecapitalized));
        style("colorTertiaryContainer", String.format("md_theme_%s_dark_tertiaryContainer", nameDecapitalized));
        style("colorOnTertiaryContainer", String.format("md_theme_%s_dark_onTertiaryContainer", nameDecapitalized));
        style("colorError", String.format("md_theme_%s_dark_error", nameDecapitalized));
        style("colorErrorContainer", String.format("md_theme_%s_dark_errorContainer", nameDecapitalized));
        style("colorOnError", String.format("md_theme_%s_dark_onError", nameDecapitalized));
        style("colorOnErrorContainer", String.format("md_theme_%s_dark_onErrorContainer", nameDecapitalized));
        style("android:colorBackground", String.format("md_theme_%s_dark_surface", nameDecapitalized));
        style("colorOnBackground", String.format("md_theme_%s_dark_onBackground", nameDecapitalized));
        style("colorSurface", String.format("md_theme_%s_dark_surface", nameDecapitalized));
        style("colorOnSurface", String.format("md_theme_%s_dark_onSurface", nameDecapitalized));
        style("colorSurfaceVariant", String.format("md_theme_%s_dark_surfaceVariant", nameDecapitalized));
        style("colorOnSurfaceVariant", String.format("md_theme_%s_dark_onSurfaceVariant", nameDecapitalized));
        style("colorOutline", String.format("md_theme_%s_dark_outline", nameDecapitalized));
        style("colorOnSurfaceInverse", String.format("md_theme_%s_dark_inverseOnSurface", nameDecapitalized));
        style("colorSurfaceInverse", String.format("md_theme_%s_dark_inverseSurface", nameDecapitalized));
        style("colorPrimaryInverse", String.format("md_theme_%s_dark_primaryInverse", nameDecapitalized));

        if (extension.getGeneratePalette().get()) {
            for (int tone : TONE_VALUES) {
                style(String.format("palettePrimary%d", tone),
                        String.format("md_theme_%s_palette_primary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteSecondary%d", tone),
                        String.format("md_theme_%s_palette_secondary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteTertiary%d", tone),
                        String.format("md_theme_%s_palette_tertiary_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteError%d", tone),
                        String.format("md_theme_%s_palette_error_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteNeutral%d", tone),
                        String.format("md_theme_%s_palette_neutral_%d", nameDecapitalized, tone));
            }
            for (int tone : TONE_VALUES) {
                style(String.format("paletteNeutralVariant%d", tone),
                        String.format("md_theme_%s_palette_neutral_variant_%d", nameDecapitalized, tone));
            }
        }
        endStyle();
    }

    private void writeTheme(MaterialThemeBuilderExtension.Theme theme) {
        CorePalette corePalette = CorePalette.of(
                Integer.parseInt(theme.getPrimaryColor().replaceFirst("#", ""), 16));

        var primaryPalette = corePalette.a1;
        var secondaryPalette = corePalette.a2;
        var tertiaryPalette = corePalette.a3;
        var errorPalette = corePalette.error;
        var neutralPalette = corePalette.n1;
        var neutralVariantPalette = corePalette.n1;

        if (theme.getSecondaryColor() != null) {
            secondaryPalette = CorePalette.of(
                    Integer.parseInt(theme.getSecondaryColor().replaceFirst("#", ""), 16)).a1;
        }

        if (theme.getTertiaryColor() != null) {
            tertiaryPalette = CorePalette.of(
                    Integer.parseInt(theme.getTertiaryColor().replaceFirst("#", ""), 16)).a1;
        }

        if (theme.getNeutralColor() != null) {
            CorePalette palette = CorePalette.of(
                    Integer.parseInt(theme.getNeutralColor().replaceFirst("#", ""), 16));
            neutralPalette = palette.n1;
            neutralVariantPalette = palette.n2;
        }

        var name = Objects.requireNonNull(theme.getName(), "Name must not be null");
        var lightThemeNameFormat = Optional.ofNullable(theme.getLightThemeFormat()).orElse("");
        var darkThemeNameFormat = Optional.ofNullable(theme.getDarkThemeFormat()).orElse("");
        var parentLightThemeName = Optional.ofNullable(theme.getLightThemeParent()).orElse("");
        var parentDarkThemeName = Optional.ofNullable(theme.getDarkThemeParent()).orElse("");

        writeColorsForTheme(primaryPalette, secondaryPalette, tertiaryPalette, errorPalette, neutralPalette, neutralVariantPalette, name);
        writeStylesForTheme(name, lightThemeNameFormat, parentLightThemeName, darkThemeNameFormat, parentDarkThemeName);
    }

    public void write() {
        var colors = Optional.ofNullable(extension.getThemes()).orElse(new ArrayList<>());

        beginResource();

        for (MaterialThemeBuilderExtension.Theme theme : colors) {
            writeTheme(theme);
        }

        endResource();
    }
}
