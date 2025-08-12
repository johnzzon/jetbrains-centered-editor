# Centered Editor Only (PhpStorm plugin)

Tiny plugin that visually centers the code editor content by adding symmetric padding.
No themes, no extra UI. Uses only public APIs.

## Build
1. Ensure JDK 17+ is installed.
2. From this folder, run:
   - macOS/Linux: `./gradlew buildPlugin`
   - Windows: `gradlew.bat buildPlugin`

The ZIP will appear under `build/distributions/*.zip` — install it via **Settings → Plugins → ⚙️ → Install Plugin from Disk…**

## Notes
- Targets PhpStorm 2024.3+ (`sinceBuild=243`). Adjust in `build.gradle.kts` if needed.
- It estimates content width from your project's *Right margin* and the editor font metrics.
