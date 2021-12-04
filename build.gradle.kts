plugins {
    kotlin("js") version "1.6.0"
}

group = "aimozg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(npm("protobufjs","6.11.2"))

}

kotlin {
    sourceSets.all {
        languageSettings {
            optIn("kotlin.js.ExperimentalJsExport")
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}
