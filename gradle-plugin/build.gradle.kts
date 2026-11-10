plugins {
    `java-gradle-plugin`
    `maven-publish`
    signing
}

group = "dev.rikka.tools.materialthemebuilder"
version = "1.6.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

tasks.javadoc {
    isFailOnError = false
}

dependencies {
    compileOnly(libs.android.gradle)
    implementation("com.google.guava:guava:33.8.0-jre")
    compileOnly("androidx.annotation:annotation:1.3.0")
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
        register("MaterialThemeBuilder") {
            id = group.toString()
            displayName = "MaterialThemeBuilder"
            description = "A gradle plugin that generates Material Design 3 themes for Android projects."
            implementationClass = "$group.MaterialThemeBuilderPlugin"
        }
    }
}

publishing {
    publications {
        withType(MavenPublication::class.java) {
            pom {
                name.convention("MaterialThemeBuilder")
                description.convention("MaterialThemeBuilder")
                url.set("https://github.com/RikkaApps/MaterialThemeBuilder")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/RikkaApps/MaterialThemeBuilder/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        name.set("RikkaW")
                        url.set("https://github.com/RikkaW")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/RikkaApps/MaterialThemeBuilder.git")
                    url.set("https://github.com/RikkaApps/MaterialThemeBuilder/")
                }
            }
        }
    }
    repositories {
        maven {
            name = "ossrh"
            val host = "https://ossrh-staging-api.central.sonatype.com"
            url = uri("$host/service/local/staging/deploy/maven2/")
            credentials(PasswordCredentials::class.java)
        }
    }
}

signing {
    val signingKey = findProperty("signingKey") as String?
    val signingPassword = findProperty("signingPassword") as String?
    val secretKeyRingFile = findProperty("signing.secretKeyRingFile") as String?
    val gnuPgDir = File(System.getProperty("user.home") + "/.gnupg/private-keys-v1.d")

    if (secretKeyRingFile != null && file(secretKeyRingFile).exists()) {
        sign(publishing.publications)
    } else if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    } else if (gnuPgDir.isDirectory && gnuPgDir.list()?.isNotEmpty() == true) {
        useGpgCmd()
        sign(publishing.publications)
    }
}
