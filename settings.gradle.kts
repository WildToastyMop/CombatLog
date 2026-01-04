pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.quiltmc.org/repository/release/")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.5"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(loader: String, vararg versions: String) {
            for (version in versions) vers("$version-$loader", version)
        }
        mc("fabric", "1.21.11", "1.21.6", "1.21.1", "1.20.1", "1.19.2", "1.16.5")
        mc("forge", "1.20.1", "1.19.2", "1.16.5")
        mc("neoforge", "1.21.11", "1.21.6", "1.21.1")
    }
    create(rootProject)
}

rootProject.name = "CombatLog"