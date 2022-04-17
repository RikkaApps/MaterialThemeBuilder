package dev.rikka.tools.materialthemebuilder.generator;

import com.google.common.base.CaseFormat;
import dev.rikka.tools.materialthemebuilder.MaterialTheme;
import dev.rikka.tools.materialthemebuilder.MaterialThemeBuilderExtension;
import dev.rikka.tools.materialthemebuilder.Util;
import palettes.CorePalette;
import palettes.TonalPalette;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class ResGenerator extends BaseResGenerator {

    private final MaterialThemeBuilderExtension extension;

    public ResGenerator(File file, MaterialThemeBuilderExtension extension) {
        super(file);
        this.extension = extension;
    }

    @Override
    protected void onGenerate() {
        if (extension.isGeneratePaletteAttributes()) {
            writePaletteAttributes();
        }
        extension.getThemes().forEach(this::writeTheme);
    }

    private void writeColorsForTheme(
            String name, TonalPalette primary, TonalPalette secondary, TonalPalette tertiary, TonalPalette error,
            TonalPalette neutral, TonalPalette neutralVariant) {

        // MDC light colors
        color(primary.tone(40), String.format("md_theme_%s_light_primary", name));
        color(primary.tone(100), String.format("md_theme_%s_light_onPrimary", name));
        color(primary.tone(90), String.format("md_theme_%s_light_primaryContainer", name));
        color(primary.tone(10), String.format("md_theme_%s_light_onPrimaryContainer", name));

        color(secondary.tone(40), String.format("md_theme_%s_light_secondary", name));
        color(secondary.tone(100), String.format("md_theme_%s_light_onSecondary", name));
        color(secondary.tone(90), String.format("md_theme_%s_light_secondaryContainer", name));
        color(secondary.tone(10), String.format("md_theme_%s_light_onSecondaryContainer", name));

        color(tertiary.tone(40), String.format("md_theme_%s_light_tertiary", name));
        color(tertiary.tone(100), String.format("md_theme_%s_light_onTertiary", name));
        color(tertiary.tone(90), String.format("md_theme_%s_light_tertiaryContainer", name));
        color(tertiary.tone(10), String.format("md_theme_%s_light_onTertiaryContainer", name));

        color(error.tone(40), String.format("md_theme_%s_light_error", name));
        color(error.tone(100), String.format("md_theme_%s_light_errorContainer", name));
        color(error.tone(90), String.format("md_theme_%s_light_onError", name));
        color(error.tone(10), String.format("md_theme_%s_light_onErrorContainer", name));

        color(neutral.tone(99), String.format("md_theme_%s_light_background", name));
        color(neutral.tone(10), String.format("md_theme_%s_light_onBackground", name));
        color(neutral.tone(99), String.format("md_theme_%s_light_surface", name));
        color(neutral.tone(10), String.format("md_theme_%s_light_onSurface", name));

        color(neutralVariant.tone(90), String.format("md_theme_%s_light_surfaceVariant", name));
        color(neutralVariant.tone(30), String.format("md_theme_%s_light_onSurfaceVariant", name));
        color(neutralVariant.tone(50), String.format("md_theme_%s_light_outline", name));

        color(neutral.tone(95), String.format("md_theme_%s_light_inverseOnSurface", name));
        color(neutral.tone(20), String.format("md_theme_%s_light_inverseSurface", name));
        color(primary.tone(80), String.format("md_theme_%s_light_primaryInverse", name));

        // MDC dark colors
        color(primary.tone(80), String.format("md_theme_%s_dark_primary", name));
        color(primary.tone(20), String.format("md_theme_%s_dark_onPrimary", name));
        color(primary.tone(30), String.format("md_theme_%s_dark_primaryContainer", name));
        color(primary.tone(90), String.format("md_theme_%s_dark_onPrimaryContainer", name));

        color(secondary.tone(80), String.format("md_theme_%s_dark_secondary", name));
        color(secondary.tone(20), String.format("md_theme_%s_dark_onSecondary", name));
        color(secondary.tone(30), String.format("md_theme_%s_dark_secondaryContainer", name));
        color(secondary.tone(90), String.format("md_theme_%s_dark_onSecondaryContainer", name));

        color(tertiary.tone(80), String.format("md_theme_%s_dark_tertiary", name));
        color(tertiary.tone(20), String.format("md_theme_%s_dark_onTertiary", name));
        color(tertiary.tone(30), String.format("md_theme_%s_dark_tertiaryContainer", name));
        color(tertiary.tone(90), String.format("md_theme_%s_dark_onTertiaryContainer", name));

        color(error.tone(80), String.format("md_theme_%s_dark_error", name));
        color(error.tone(20), String.format("md_theme_%s_dark_errorContainer", name));
        color(error.tone(30), String.format("md_theme_%s_dark_onError", name));
        color(error.tone(90), String.format("md_theme_%s_dark_onErrorContainer", name));

        color(neutral.tone(10), String.format("md_theme_%s_dark_background", name));
        color(neutral.tone(90), String.format("md_theme_%s_dark_onBackground", name));
        color(neutral.tone(10), String.format("md_theme_%s_dark_surface", name));
        color(neutral.tone(80), String.format("md_theme_%s_dark_onSurface", name));

        color(neutralVariant.tone(30), String.format("md_theme_%s_dark_surfaceVariant", name));
        color(neutralVariant.tone(80), String.format("md_theme_%s_dark_onSurfaceVariant", name));
        color(neutralVariant.tone(60), String.format("md_theme_%s_dark_outline", name));

        color(neutral.tone(40), String.format("md_theme_%s_dark_inverseOnSurface", name));
        color(neutral.tone(90), String.format("md_theme_%s_dark_inverseSurface", name));
        color(primary.tone(40), String.format("md_theme_%s_dark_primaryInverse", name));

        // MD3 color tokens
        if (extension.isGeneratePalette()) {
            for (int tone : MaterialTheme.TONE_VALUES) {
                color(primary.tone(tone), String.format("md_theme_%s_palette_primary_%d", name, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                color(secondary.tone(tone), String.format("md_theme_%s_palette_secondary_%d", name, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                color(tertiary.tone(tone), String.format("md_theme_%s_palette_tertiary_%d", name, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                color(error.tone(tone), String.format("md_theme_%s_palette_error_%d", name, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                color(neutral.tone(tone), String.format("md_theme_%s_palette_neutral_%d", name, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                color(neutralVariant.tone(tone), String.format("md_theme_%s_palette_neutral_variant_%d", name, tone));
            }
        }
    }

    private void writeStylesForTheme(
            String nameLowerUnderScore, String nameUpperCamel,
            String lightThemeNameFormat, String parentLightThemeName,
            String darkThemeNameFormat, String parentDarkThemeName) {

        beginStyle(String.format(lightThemeNameFormat, nameUpperCamel), parentLightThemeName);
        style("colorPrimary", String.format("md_theme_%s_light_primary", nameLowerUnderScore));
        style("colorOnPrimary", String.format("md_theme_%s_light_onPrimary", nameLowerUnderScore));
        style("colorPrimaryContainer", String.format("md_theme_%s_light_primaryContainer", nameLowerUnderScore));
        style("colorOnPrimaryContainer", String.format("md_theme_%s_light_onPrimaryContainer", nameLowerUnderScore));
        style("colorSecondary", String.format("md_theme_%s_light_secondary", nameLowerUnderScore));
        style("colorOnSecondary", String.format("md_theme_%s_light_onSecondary", nameLowerUnderScore));
        style("colorSecondaryContainer", String.format("md_theme_%s_light_secondaryContainer", nameLowerUnderScore));
        style("colorOnSecondaryContainer", String.format("md_theme_%s_light_onSecondaryContainer", nameLowerUnderScore));
        style("colorTertiary", String.format("md_theme_%s_light_tertiary", nameLowerUnderScore));
        style("colorOnTertiary", String.format("md_theme_%s_light_onTertiary", nameLowerUnderScore));
        style("colorTertiaryContainer", String.format("md_theme_%s_light_tertiaryContainer", nameLowerUnderScore));
        style("colorOnTertiaryContainer", String.format("md_theme_%s_light_onTertiaryContainer", nameLowerUnderScore));
        style("colorError", String.format("md_theme_%s_light_error", nameLowerUnderScore));
        style("colorErrorContainer", String.format("md_theme_%s_light_errorContainer", nameLowerUnderScore));
        style("colorOnError", String.format("md_theme_%s_light_onError", nameLowerUnderScore));
        style("colorOnErrorContainer", String.format("md_theme_%s_light_onErrorContainer", nameLowerUnderScore));
        style("android:colorBackground", String.format("md_theme_%s_light_surface", nameLowerUnderScore));
        style("colorOnBackground", String.format("md_theme_%s_light_onBackground", nameLowerUnderScore));
        style("colorSurface", String.format("md_theme_%s_light_surface", nameLowerUnderScore));
        style("colorOnSurface", String.format("md_theme_%s_light_onSurface", nameLowerUnderScore));
        style("colorSurfaceVariant", String.format("md_theme_%s_light_surfaceVariant", nameLowerUnderScore));
        style("colorOnSurfaceVariant", String.format("md_theme_%s_light_onSurfaceVariant", nameLowerUnderScore));
        style("colorOutline", String.format("md_theme_%s_light_outline", nameLowerUnderScore));
        style("colorOnSurfaceInverse", String.format("md_theme_%s_light_inverseOnSurface", nameLowerUnderScore));
        style("colorSurfaceInverse", String.format("md_theme_%s_light_inverseSurface", nameLowerUnderScore));
        style("colorPrimaryInverse", String.format("md_theme_%s_light_primaryInverse", nameLowerUnderScore));

        if (extension.isGeneratePalette()) {
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("palettePrimary%d", tone),
                        String.format("md_theme_%s_palette_primary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteSecondary%d", tone),
                        String.format("md_theme_%s_palette_secondary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteTertiary%d", tone),
                        String.format("md_theme_%s_palette_tertiary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteError%d", tone),
                        String.format("md_theme_%s_palette_error_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteNeutral%d", tone),
                        String.format("md_theme_%s_palette_neutral_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteNeutralVariant%d", tone),
                        String.format("md_theme_%s_palette_neutral_variant_%d", nameLowerUnderScore, tone));
            }
        }
        endStyle();

        beginStyle(String.format(darkThemeNameFormat, nameUpperCamel), parentDarkThemeName);
        style("colorPrimary", String.format("md_theme_%s_dark_primary", nameLowerUnderScore));
        style("colorOnPrimary", String.format("md_theme_%s_dark_onPrimary", nameLowerUnderScore));
        style("colorPrimaryContainer", String.format("md_theme_%s_dark_primaryContainer", nameLowerUnderScore));
        style("colorOnPrimaryContainer", String.format("md_theme_%s_dark_onPrimaryContainer", nameLowerUnderScore));
        style("colorSecondary", String.format("md_theme_%s_dark_secondary", nameLowerUnderScore));
        style("colorOnSecondary", String.format("md_theme_%s_dark_onSecondary", nameLowerUnderScore));
        style("colorSecondaryContainer", String.format("md_theme_%s_dark_secondaryContainer", nameLowerUnderScore));
        style("colorOnSecondaryContainer", String.format("md_theme_%s_dark_onSecondaryContainer", nameLowerUnderScore));
        style("colorTertiary", String.format("md_theme_%s_dark_tertiary", nameLowerUnderScore));
        style("colorOnTertiary", String.format("md_theme_%s_dark_onTertiary", nameLowerUnderScore));
        style("colorTertiaryContainer", String.format("md_theme_%s_dark_tertiaryContainer", nameLowerUnderScore));
        style("colorOnTertiaryContainer", String.format("md_theme_%s_dark_onTertiaryContainer", nameLowerUnderScore));
        style("colorError", String.format("md_theme_%s_dark_error", nameLowerUnderScore));
        style("colorErrorContainer", String.format("md_theme_%s_dark_errorContainer", nameLowerUnderScore));
        style("colorOnError", String.format("md_theme_%s_dark_onError", nameLowerUnderScore));
        style("colorOnErrorContainer", String.format("md_theme_%s_dark_onErrorContainer", nameLowerUnderScore));
        style("android:colorBackground", String.format("md_theme_%s_dark_surface", nameLowerUnderScore));
        style("colorOnBackground", String.format("md_theme_%s_dark_onBackground", nameLowerUnderScore));
        style("colorSurface", String.format("md_theme_%s_dark_surface", nameLowerUnderScore));
        style("colorOnSurface", String.format("md_theme_%s_dark_onSurface", nameLowerUnderScore));
        style("colorSurfaceVariant", String.format("md_theme_%s_dark_surfaceVariant", nameLowerUnderScore));
        style("colorOnSurfaceVariant", String.format("md_theme_%s_dark_onSurfaceVariant", nameLowerUnderScore));
        style("colorOutline", String.format("md_theme_%s_dark_outline", nameLowerUnderScore));
        style("colorOnSurfaceInverse", String.format("md_theme_%s_dark_inverseOnSurface", nameLowerUnderScore));
        style("colorSurfaceInverse", String.format("md_theme_%s_dark_inverseSurface", nameLowerUnderScore));
        style("colorPrimaryInverse", String.format("md_theme_%s_dark_primaryInverse", nameLowerUnderScore));

        if (extension.isGeneratePalette()) {
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("palettePrimary%d", tone),
                        String.format("md_theme_%s_palette_primary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteSecondary%d", tone),
                        String.format("md_theme_%s_palette_secondary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteTertiary%d", tone),
                        String.format("md_theme_%s_palette_tertiary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteError%d", tone),
                        String.format("md_theme_%s_palette_error_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteNeutral%d", tone),
                        String.format("md_theme_%s_palette_neutral_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                style(String.format("paletteNeutralVariant%d", tone),
                        String.format("md_theme_%s_palette_neutral_variant_%d", nameLowerUnderScore, tone));
            }
        }
        endStyle();
    }

    private void writeTheme(MaterialThemeBuilderExtension.Theme theme) {
        var name = Util.capitalize(Objects.requireNonNull(theme.getName(), "Name must not be null")
                .replaceAll("-", "_"));
        var lightThemeNameFormat = Optional.ofNullable(theme.getLightThemeFormat()).orElse("");
        var darkThemeNameFormat = Optional.ofNullable(theme.getDarkThemeFormat()).orElse("");
        var parentLightThemeName = Optional.ofNullable(theme.getLightThemeParent()).orElse("");
        var parentDarkThemeName = Optional.ofNullable(theme.getDarkThemeParent()).orElse("");

        String nameUpperCamel;
        String nameLowerUnderScore;
        var nameIsUnderScore = name.contains("_");
        if (nameIsUnderScore) {
            name = name.toLowerCase(Locale.ROOT);
            nameUpperCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
            nameLowerUnderScore = name;
        } else {
            nameUpperCamel = name;
            nameLowerUnderScore = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        }

        if (theme.getPrimaryColor() == null && theme.isDynamicColors()) {
            beginStyle(String.format(lightThemeNameFormat, nameUpperCamel), parentLightThemeName);
            endStyle();
            beginStyle(String.format(darkThemeNameFormat, nameUpperCamel), parentDarkThemeName);
            endStyle();
            return;
        }

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

        writeColorsForTheme(nameLowerUnderScore, primaryPalette, secondaryPalette, tertiaryPalette, errorPalette, neutralPalette, neutralVariantPalette);
        writeStylesForTheme(nameLowerUnderScore, nameUpperCamel, lightThemeNameFormat, parentLightThemeName, darkThemeNameFormat, parentDarkThemeName);
    }

    private void writePaletteAttributes() {
        beginDeclareStyleable("MaterialColorTokens");
        String format = "color|reference";
        String[] palettes = new String[]{
                "Primary", "Secondary", "Tertiary", "Error", "Neutral", "NeutralVariant"
        };

        for (String palette : palettes) {
            for (int tone : MaterialTheme.TONE_VALUES) {
                attr(String.format(Locale.ROOT, "palette%s%d", palette, tone), format);
            }
        }

        endDeclareStyleable();
    }
}
