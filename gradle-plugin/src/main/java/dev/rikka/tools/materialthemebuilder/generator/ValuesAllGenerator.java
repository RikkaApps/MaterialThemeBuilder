package dev.rikka.tools.materialthemebuilder.generator;

import blend.Blend;
import com.google.common.base.CaseFormat;
import dev.rikka.tools.materialthemebuilder.MaterialTheme;
import dev.rikka.tools.materialthemebuilder.MaterialThemeBuilderExtension;
import dev.rikka.tools.materialthemebuilder.Util;
import hct.Hct;
import palettes.CorePalette;
import palettes.TonalPalette;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class ValuesAllGenerator extends ValuesGenerator {

    private final MaterialThemeBuilderExtension extension;

    public ValuesAllGenerator(File file, MaterialThemeBuilderExtension extension) {
        super(file);
        this.extension = extension;
    }

    @Override
    protected void onGenerate() {
        if (extension.isGeneratePaletteAttributes()) {
            writePaletteAttributes();
        }
        if (extension.isGenerateTextColors()) {
            writeTextColorsAttributes();
        }
        extension.getExtendedColors().forEach(this::writeExtendedColorsAttributes);
        extension.getThemes().forEach(this::writeTheme);
    }

    private void writeTextColorsAttributes() {
        beginDeclareStyleable("MaterialTextColors");
        String format = "color|reference";

        for (String textColor : MaterialTheme.TEXT_COLORS) {
            for (String emphasis : MaterialTheme.TEXT_COLOR_EMPHASIS) {
                String name = "text"
                        + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, textColor)
                        + emphasis
                        + ("".equals(emphasis) ? "" : "Emphasis");

                attr(name, format);
            }
        }

        endDeclareStyleable();

        floatDimen("m3_emphasis_disabled", "0.38");
        floatDimen("m3_emphasis_disabled_background", "0.12");
        floatDimen("m3_emphasis_high", "0.87");
        floatDimen("m3_emphasis_high_disabled", "0.33");
        floatDimen("m3_emphasis_medium", "0.6");
        floatDimen("m3_emphasis_medium_disabled", "0.23");
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

    private void writeExtendedColorsAttributes(MaterialThemeBuilderExtension.ExtendedColor extendedColor) {
        String name = extendedColor.getNameForAttribute();
        beginDeclareStyleable("MaterialColor" + name);
        for (MaterialTheme.Color color : MaterialTheme.COLORS) {
            attr(color.getAttributeName(name), "color|reference");
        }
        attr("harmonize" + name, "boolean");
        endDeclareStyleable();
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
        color(error.tone(100), String.format("md_theme_%s_light_onError", name));
        color(error.tone(90), String.format("md_theme_%s_light_errorContainer", name));
        color(error.tone(10), String.format("md_theme_%s_light_onErrorContainer", name));

        color(neutral.tone(98), String.format("md_theme_%s_light_background", name));
        color(neutral.tone(10), String.format("md_theme_%s_light_onBackground", name));
        color(neutral.tone(98), String.format("md_theme_%s_light_surface", name));
        color(neutral.tone(10), String.format("md_theme_%s_light_onSurface", name));

        color(neutral.tone(94), String.format("md_theme_%s_light_container", name));
        color(neutral.tone(96), String.format("md_theme_%s_light_containerLow", name));
        color(neutral.tone(100), String.format("md_theme_%s_light_containerLowest", name));
        color(neutral.tone(92), String.format("md_theme_%s_light_containerHigh", name));
        color(neutral.tone(90), String.format("md_theme_%s_light_containerHighest", name));

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
        color(error.tone(20), String.format("md_theme_%s_dark_onError", name));
        color(error.tone(30), String.format("md_theme_%s_dark_errorContainer", name));
        color(error.tone(90), String.format("md_theme_%s_dark_onErrorContainer", name));

        color(neutral.tone(6), String.format("md_theme_%s_dark_background", name));
        color(neutral.tone(90), String.format("md_theme_%s_dark_onBackground", name));
        color(neutral.tone(6), String.format("md_theme_%s_dark_surface", name));
        color(neutral.tone(80), String.format("md_theme_%s_dark_onSurface", name));

        color(neutral.tone(12), String.format("md_theme_%s_dark_container", name));
        color(neutral.tone(10), String.format("md_theme_%s_dark_containerLow", name));
        color(neutral.tone(4), String.format("md_theme_%s_dark_containerLowest", name));
        color(neutral.tone(17), String.format("md_theme_%s_dark_containerHigh", name));
        color(neutral.tone(22), String.format("md_theme_%s_dark_containerHighest", name));

        color(neutralVariant.tone(30), String.format("md_theme_%s_dark_surfaceVariant", name));
        color(neutralVariant.tone(80), String.format("md_theme_%s_dark_onSurfaceVariant", name));
        color(neutralVariant.tone(60), String.format("md_theme_%s_dark_outline", name));

        color(neutral.tone(20), String.format("md_theme_%s_dark_inverseOnSurface", name));
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
        styleColorRef("colorPrimary", String.format("md_theme_%s_light_primary", nameLowerUnderScore));
        styleColorRef("colorOnPrimary", String.format("md_theme_%s_light_onPrimary", nameLowerUnderScore));
        styleColorRef("colorPrimaryContainer", String.format("md_theme_%s_light_primaryContainer", nameLowerUnderScore));
        styleColorRef("colorOnPrimaryContainer", String.format("md_theme_%s_light_onPrimaryContainer", nameLowerUnderScore));
        styleColorRef("colorSecondary", String.format("md_theme_%s_light_secondary", nameLowerUnderScore));
        styleColorRef("colorOnSecondary", String.format("md_theme_%s_light_onSecondary", nameLowerUnderScore));
        styleColorRef("colorSecondaryContainer", String.format("md_theme_%s_light_secondaryContainer", nameLowerUnderScore));
        styleColorRef("colorOnSecondaryContainer", String.format("md_theme_%s_light_onSecondaryContainer", nameLowerUnderScore));
        styleColorRef("colorTertiary", String.format("md_theme_%s_light_tertiary", nameLowerUnderScore));
        styleColorRef("colorOnTertiary", String.format("md_theme_%s_light_onTertiary", nameLowerUnderScore));
        styleColorRef("colorTertiaryContainer", String.format("md_theme_%s_light_tertiaryContainer", nameLowerUnderScore));
        styleColorRef("colorOnTertiaryContainer", String.format("md_theme_%s_light_onTertiaryContainer", nameLowerUnderScore));
        styleColorRef("colorError", String.format("md_theme_%s_light_error", nameLowerUnderScore));
        styleColorRef("colorErrorContainer", String.format("md_theme_%s_light_errorContainer", nameLowerUnderScore));
        styleColorRef("colorOnError", String.format("md_theme_%s_light_onError", nameLowerUnderScore));
        styleColorRef("colorOnErrorContainer", String.format("md_theme_%s_light_onErrorContainer", nameLowerUnderScore));
        styleColorRef("android:colorBackground", String.format("md_theme_%s_light_surface", nameLowerUnderScore));
        styleColorRef("colorOnBackground", String.format("md_theme_%s_light_onBackground", nameLowerUnderScore));
        styleColorRef("colorSurface", String.format("md_theme_%s_light_surface", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainer", String.format("md_theme_%s_light_container", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerLow", String.format("md_theme_%s_light_containerLow", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerLowest", String.format("md_theme_%s_light_containerLowest", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerHigh", String.format("md_theme_%s_light_containerHigh", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerHighest", String.format("md_theme_%s_light_containerHighest", nameLowerUnderScore));
        styleColorRef("colorOnSurface", String.format("md_theme_%s_light_onSurface", nameLowerUnderScore));
        styleColorRef("colorSurfaceVariant", String.format("md_theme_%s_light_surfaceVariant", nameLowerUnderScore));
        styleColorRef("colorOnSurfaceVariant", String.format("md_theme_%s_light_onSurfaceVariant", nameLowerUnderScore));
        styleColorRef("colorOutline", String.format("md_theme_%s_light_outline", nameLowerUnderScore));
        styleColorRef("colorOnSurfaceInverse", String.format("md_theme_%s_light_inverseOnSurface", nameLowerUnderScore));
        styleColorRef("colorSurfaceInverse", String.format("md_theme_%s_light_inverseSurface", nameLowerUnderScore));
        styleColorRef("colorPrimaryInverse", String.format("md_theme_%s_light_primaryInverse", nameLowerUnderScore));

        // Extended colors
        for (MaterialThemeBuilderExtension.ExtendedColor extendedColor : extension.getExtendedColors()) {
            for (MaterialTheme.Color color : MaterialTheme.COLORS) {
                styleColorRef(
                        color.getAttributeName(extendedColor.getNameForAttribute()),
                        color.getFileName(nameLowerUnderScore, true, extendedColor.getNameForAttribute()));
            }
            style("harmonize" + extendedColor.getNameForAttribute(), Boolean.toString(extendedColor.isHarmonize()));
        }

        if (extension.isGeneratePalette()) {
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("palettePrimary%d", tone),
                        String.format("md_theme_%s_palette_primary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteSecondary%d", tone),
                        String.format("md_theme_%s_palette_secondary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteTertiary%d", tone),
                        String.format("md_theme_%s_palette_tertiary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteError%d", tone),
                        String.format("md_theme_%s_palette_error_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteNeutral%d", tone),
                        String.format("md_theme_%s_palette_neutral_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteNeutralVariant%d", tone),
                        String.format("md_theme_%s_palette_neutral_variant_%d", nameLowerUnderScore, tone));
            }
        }
        if (extension.isGenerateTextColors()) {
            textColorStyles();
        }
        endStyle();

        beginStyle(String.format(darkThemeNameFormat, nameUpperCamel), parentDarkThemeName);
        styleColorRef("colorPrimary", String.format("md_theme_%s_dark_primary", nameLowerUnderScore));
        styleColorRef("colorOnPrimary", String.format("md_theme_%s_dark_onPrimary", nameLowerUnderScore));
        styleColorRef("colorPrimaryContainer", String.format("md_theme_%s_dark_primaryContainer", nameLowerUnderScore));
        styleColorRef("colorOnPrimaryContainer", String.format("md_theme_%s_dark_onPrimaryContainer", nameLowerUnderScore));
        styleColorRef("colorSecondary", String.format("md_theme_%s_dark_secondary", nameLowerUnderScore));
        styleColorRef("colorOnSecondary", String.format("md_theme_%s_dark_onSecondary", nameLowerUnderScore));
        styleColorRef("colorSecondaryContainer", String.format("md_theme_%s_dark_secondaryContainer", nameLowerUnderScore));
        styleColorRef("colorOnSecondaryContainer", String.format("md_theme_%s_dark_onSecondaryContainer", nameLowerUnderScore));
        styleColorRef("colorTertiary", String.format("md_theme_%s_dark_tertiary", nameLowerUnderScore));
        styleColorRef("colorOnTertiary", String.format("md_theme_%s_dark_onTertiary", nameLowerUnderScore));
        styleColorRef("colorTertiaryContainer", String.format("md_theme_%s_dark_tertiaryContainer", nameLowerUnderScore));
        styleColorRef("colorOnTertiaryContainer", String.format("md_theme_%s_dark_onTertiaryContainer", nameLowerUnderScore));
        styleColorRef("colorError", String.format("md_theme_%s_dark_error", nameLowerUnderScore));
        styleColorRef("colorErrorContainer", String.format("md_theme_%s_dark_errorContainer", nameLowerUnderScore));
        styleColorRef("colorOnError", String.format("md_theme_%s_dark_onError", nameLowerUnderScore));
        styleColorRef("colorOnErrorContainer", String.format("md_theme_%s_dark_onErrorContainer", nameLowerUnderScore));
        styleColorRef("android:colorBackground", String.format("md_theme_%s_dark_surface", nameLowerUnderScore));
        styleColorRef("colorOnBackground", String.format("md_theme_%s_dark_onBackground", nameLowerUnderScore));
        styleColorRef("colorSurface", String.format("md_theme_%s_dark_surface", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainer", String.format("md_theme_%s_dark_container", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerLow", String.format("md_theme_%s_dark_containerLow", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerLowest", String.format("md_theme_%s_dark_containerLowest", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerHigh", String.format("md_theme_%s_dark_containerHigh", nameLowerUnderScore));
        styleColorRef("colorSurfaceContainerHighest", String.format("md_theme_%s_dark_containerHighest", nameLowerUnderScore));
        styleColorRef("colorOnSurface", String.format("md_theme_%s_dark_onSurface", nameLowerUnderScore));
        styleColorRef("colorSurfaceVariant", String.format("md_theme_%s_dark_surfaceVariant", nameLowerUnderScore));
        styleColorRef("colorOnSurfaceVariant", String.format("md_theme_%s_dark_onSurfaceVariant", nameLowerUnderScore));
        styleColorRef("colorOutline", String.format("md_theme_%s_dark_outline", nameLowerUnderScore));
        styleColorRef("colorOnSurfaceInverse", String.format("md_theme_%s_dark_inverseOnSurface", nameLowerUnderScore));
        styleColorRef("colorSurfaceInverse", String.format("md_theme_%s_dark_inverseSurface", nameLowerUnderScore));
        styleColorRef("colorPrimaryInverse", String.format("md_theme_%s_dark_primaryInverse", nameLowerUnderScore));

        // Extended colors
        for (MaterialThemeBuilderExtension.ExtendedColor extendedColor : extension.getExtendedColors()) {
            for (MaterialTheme.Color color : MaterialTheme.COLORS) {
                styleColorRef(
                        color.getAttributeName(extendedColor.getNameForAttribute()),
                        color.getFileName(nameLowerUnderScore, false, extendedColor.getNameForAttribute()));
            }
            style("harmonize" + extendedColor.getNameForAttribute(), Boolean.toString(extendedColor.isHarmonize()));
        }

        if (extension.isGeneratePalette()) {
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("palettePrimary%d", tone),
                        String.format("md_theme_%s_palette_primary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteSecondary%d", tone),
                        String.format("md_theme_%s_palette_secondary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteTertiary%d", tone),
                        String.format("md_theme_%s_palette_tertiary_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteError%d", tone),
                        String.format("md_theme_%s_palette_error_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteNeutral%d", tone),
                        String.format("md_theme_%s_palette_neutral_%d", nameLowerUnderScore, tone));
            }
            for (int tone : MaterialTheme.TONE_VALUES) {
                styleColorRef(String.format("paletteNeutralVariant%d", tone),
                        String.format("md_theme_%s_palette_neutral_variant_%d", nameLowerUnderScore, tone));
            }
        }

        if (extension.isGenerateTextColors()) {
            textColorStyles();
        }
        endStyle();
    }

    private void writeExtendedColors(
            MaterialThemeBuilderExtension.ExtendedColor extendedColor,
            String nameLowerUnderScore,
            int primaryColorLight, int primaryColorDark) {

        var extendedColorName = extendedColor.getNameForAttribute();
        var extendedColorInt = Integer.parseInt(extendedColor.getColor().replaceFirst("#", ""), 16);

        {
            int extendedColorHarmonize = extendedColorInt;
            if (extendedColor.isHarmonize()) {
                extendedColorHarmonize = Blend.harmonize(extendedColorInt, primaryColorLight);
            }
            var hctColor = Hct.fromInt(extendedColorHarmonize);

            for (MaterialTheme.Color color : MaterialTheme.COLORS) {
                hctColor.setTone(color.getToneLight());
                color(hctColor.toInt(), color.getFileName(nameLowerUnderScore, true, extendedColorName));
            }
        }

        {
            int extendedColorHarmonize = extendedColorInt;
            if (extendedColor.isHarmonize()) {
                extendedColorHarmonize = Blend.harmonize(extendedColorInt, primaryColorDark);
            }
            var hctColor = Hct.fromInt(extendedColorHarmonize);

            for (MaterialTheme.Color color : MaterialTheme.COLORS) {
                hctColor.setTone(color.getToneDark());
                color(hctColor.toInt(), color.getFileName(nameLowerUnderScore, false, extendedColorName));
            }
        }
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

        for (MaterialThemeBuilderExtension.ExtendedColor extendedColor : extension.getExtendedColors()) {
            writeExtendedColors(extendedColor, nameLowerUnderScore, primaryPalette.tone(40), primaryPalette.tone(70));
        }
        writeStylesForTheme(nameLowerUnderScore, nameUpperCamel, lightThemeNameFormat, parentLightThemeName, darkThemeNameFormat, parentDarkThemeName);
    }
}
