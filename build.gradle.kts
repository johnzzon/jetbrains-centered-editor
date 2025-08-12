plugins {
  id("org.jetbrains.intellij") version "1.17.3"
  kotlin("jvm") version "2.2.0"
}

group = "dev.centered"
version = "1.0.0"

repositories { mavenCentral() }
kotlin { jvmToolchain(17) }

intellij {
  version.set("2025.2")
  type.set("PS")
  plugins.set(listOf())
}

tasks.patchPluginXml {
  sinceBuild.set("252")
  untilBuild.set("*")
}

tasks.buildSearchableOptions { enabled = false }

// ⚠️ no `dependencies { implementation(kotlin("stdlib")) }`
