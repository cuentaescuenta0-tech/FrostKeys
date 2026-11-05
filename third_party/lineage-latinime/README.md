# LineageOS LatinIME source snapshot

This directory contains source and resources from `LineageOS/android_packages_inputmethods_LatinIME`, branch `lineage-24.0`, commit `072efed63fcb6bc28650abb629b592820b2d918d`.

The app compiles these sources through the `:lineage-ime-core` Android library module. Source paths are kept under their original `com.android.inputmethod.*` packages and retain their upstream copyright/license headers. Upstream notices are preserved in [`NOTICE`](NOTICE) and [`java/NOTICE`](java/NOTICE).

Compatibility adaptation: LatinIME's keyboard-theme `themeId` attr uses a different enum domain from FrostKeys. The vendored LatinIME attr and its Java/XML references are named `lineageThemeId` to avoid Android resource-merger collisions; the numeric theme values are unchanged.

Upstream repository: https://github.com/LineageOS/android_packages_inputmethods_LatinIME
