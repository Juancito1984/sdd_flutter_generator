plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.jetbrains.intellij.platform") version "2.20.0"
}

group = "com.nyk"
version = "1.0.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
        intellijDependencies()
    }
}

dependencies {
    intellijPlatform {
        androidStudio("2024.1.1.13")
        bundledPlugin("com.intellij.java")
        pluginVerifier()
    }
    testImplementation(kotlin("test"))
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild.set("241")
            untilBuild.set(provider { null })
        }
    }
    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    named("buildSearchableOptions") {
        enabled = false
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
