plugins {
    java
    `java-gradle-plugin`
    `maven-publish`
    signing
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.android.gradle)
}

sourceSets {
    main {
        java {
            srcDir("$rootDir/material-color-utilities/java")
        }
        java.sourceDirectories.files += File("$rootDir/material-color-utilities/java")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

gradlePlugin {
    plugins {
        create("MaterialThemeBuilder") {
            id = project.group.toString()
            displayName = "MaterialThemeBuilder"
            description = "A gradle plugin that generates Material Design 3 theme for Android projects."
            implementationClass = "$id.MaterialThemeBuilderPlugin"
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            named("pluginMaven", MavenPublication::class) {
                artifact(tasks["sourcesJar"])
                artifact(tasks["javadocJar"])
            }
        }
    }

    tasks.withType(Jar::class) {
        manifest {
            attributes(mapOf("Implementation-Version" to project.version.toString()))
        }
    }
}
