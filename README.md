# Centered Editor Only (PhpStorm plugin)

Tiny plugin that visually centers the code editor content by adding left padding.
No themes, no extra UI. Uses only public APIs.

## Build
1. Ensure JDK 17+ is installed.
2. From this folder, run:
   - macOS/Linux: `./gradlew buildPlugin`
   - Windows: `gradlew.bat buildPlugin`

The ZIP will appear under `build/distributions/*.zip` — install it via **Settings → Plugins → ⚙️ → Install Plugin from Disk…**

## How It Works

The plugin automatically centers editor content by:
- Calculating optimal width for 80 columns based on font metrics
- Adding left padding to center content while keeping scrollbars at the right edge
- Adjusting padding dynamically when windows are resized

## Configuration

The plugin includes configurable settings via `CenteredSettings`:
- `minSidePaddingPx`: Minimum padding (default: 24px)
- `extraGutterAllowancePx`: Extra space allowance for gutter (default: 32px)

## Notes
- Targets PhpStorm 2025.2+ (`sinceBuild=252`). Adjust in `build.gradle.kts` if needed.
- Uses 80-column target width with monospace font metrics for calculations.
