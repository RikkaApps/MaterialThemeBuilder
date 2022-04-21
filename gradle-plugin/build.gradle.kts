plugins {
    java
    `java-gradle-plugin`
    `maven-publish`
    signing
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.android.gradle)
    implementation("com.google.guava:guava:31.1-jre")
}

sourceSets {
    main {
        java {
            srcDir("$rootDir/material-color-utilities/java")
        }
    }
}

gradlePlugin {
    plugins {
        create("MaterialThemeBuilder") {
            id = project.group.toString()
            displayName = "MaterialThemeBuilder"
            description = "A gradle plugin that generates Material Design 3 themes for Android projects."
            implementationClass = "$id.MaterialThemeBuilderPlugin"
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            named("pluginMaven", MavenPublication::class) {
                artifactId = "gradle-plugin"

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
