plugins {
    kotlin("js") version "1.6.10"
}

group = "aimozg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(npm("protobufjs","6.11.2"))
    implementation(npm("sass","^1.44.0"))
    implementation(npm("sass-loader","^12.3.0"))

}

kotlin {
    /*sourceSets.all {
        languageSettings {
            optIn("kotlin.js.ExperimentalJsExport")
            optIn("kotlin.time.ExperimentalTime")
        }
    }*/
	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
		kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.js.ExperimentalJsExport"
		kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
	}
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}
