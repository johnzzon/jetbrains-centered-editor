# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a PhpStorm/IntelliJ plugin called "Centered Editor Only" that centers editor content by adding symmetric padding. The plugin is minimal and uses only public IntelliJ APIs.

## Build System

This project uses Gradle with the IntelliJ Platform plugin. Key commands:

- **Build plugin**: `./gradlew buildPlugin` (macOS/Linux) or `gradlew.bat buildPlugin` (Windows)  
- **Run in development**: `./gradlew runIde`
- **Verify plugin**: `./gradlew verifyPlugin`

The built plugin ZIP appears in `build/distributions/` and can be installed via **Settings → Plugins → ⚙️ → Install Plugin from Disk...**

## Architecture

The plugin consists of two main components:

1. **CenteredEditorListener** (`src/main/kotlin/dev/centered/CenteredEditorListener.kt`): 
   - Implements `EditorFactoryListener` to intercept editor creation
   - Applies centering by calculating optimal padding based on font metrics and target column width (120 columns)
   - Handles window resizing via component listeners

2. **CenteredSettings** (`src/main/kotlin/dev/centered/CenteredSettings.kt`):
   - Application-level service storing configuration
   - `minSidePaddingPx`: Minimum padding (default: 24px)
   - `extraGutterAllowancePx`: Extra space allowance (default: 32px)

The plugin is registered via `plugin.xml` as both an `editorFactoryListener` and `applicationService`.

## Target Platform

- Targets PhpStorm 2025.2+ (sinceBuild=252)
- Built with Kotlin JVM toolchain 17
- No external dependencies beyond IntelliJ platform