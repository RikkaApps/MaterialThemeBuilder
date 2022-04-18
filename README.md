# MaterialThemeBuilderPlugin

Same as Google's [Material Theme Builder](https://material-foundation.github.io/material-theme-builder), but as a gradle plugin.

## Usage

![gradle-plugin](https://img.shields.io/maven-central/v/dev.rikka.tools.materialthemebuilder/dev.rikka.tools.materialthemebuilder.gradle.plugin?label=gradle-plugin)

Replace all the `<version>` below with the version shows here.

1. Add gradle plugin
  
   ```groovy
   // "old way"
   buildscript {
       repositories {
           mavenCentral()
       }
       dependencies {
           classpath 'dev.rikka.tools.materialthemebuilder:gradle-plugin:<version>'
       }
   }
   ```

   ```groovy
   // "new way"
   plugins {
       id 'dev.rikka.tools.materialthemebuilder' version '<version>'
   }
   ```

2. Use the plugin in Android application or library module

   ```groovy
   plugins {
       id('dev.rikka.tools.materialthemebuilder')
   }

3. Config

   ```groovy
   materialThemeBuilder {
       // List of themes to generate
       themes {
           // Name of the theme
           theme1 {
               // Primary color, acts as the source color
               primaryColor = "#3F51B5"
               
               // Optional colors, override colors calculated from the source color
               //secondaryColor = "#000000"
               //tertiaryColor = "#000000"
               //neutralColor = "#000000"
               
               // Use dynamic colors on API 31+
               dynamicColors = false
               
               lightThemeFormat = "Theme.Material3.Light.%s"
               lightThemeParent = "Theme.Material3.Light.Rikka"
               darkThemeFormat = "Theme.Material3.Dark.%s"
               darkThemeParent = "Theme.Material3.Dark.Rikka"
           }
           theme2 {
               // ...
           }
       }
   
       // Add Material Design 3 color tokens (such as palettePrimary100) in generated theme
       // rikka.material:material >= 2.0.0 provides such attributes
       // Enable this if your are using rikka.material:material
       generatePalette = false
       
       // Generate Material Design 3 color tokens attributes (such as palettePrimary100)
       // When necessary, you can use this if your are not using rikka.material:material
       generatePaletteAttributes = false
       
       // Generate "textColorOnXxx" attributes such as "textColorOnPrimary", "textColorOnPrimaryHighEmphasis"
       // and "textColorOnPrimaryMediumEmphasis"
       generateTextColors = false
   }
   ```