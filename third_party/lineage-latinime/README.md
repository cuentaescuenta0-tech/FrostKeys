# LineageOS LatinIME source snapshot

This directory contains source and resources from `LineageOS/android_packages_inputmethods_LatinIME`, branch `lineage-24.0`, commit `072efed63fcb6bc28650abb629b592820b2d918d`.

The app compiles these sources through the `:lineage-ime-core` Android library module. Source paths are kept under their original `com.android.inputmethod.*` packages and retain their upstream copyright/license headers. Upstream notices are preserved in [`NOTICE`](NOTICE) and [`java/NOTICE`](java/NOTICE).

Build compatibility adaptations:

- LatinIME's keyboard-theme `themeId` attr uses a different enum domain from FrostKeys. The vendored LatinIME attr and its Java/XML references are named `lineageThemeId` to avoid Android resource-merger collisions; numeric theme values are unchanged.
- Upstream resource XML uses the package URI `res/com.android.inputmethod.latin` from its standalone app build. The library build makes a generated copy with that URI rewritten to `res-auto`; the vendored source snapshot remains untouched by this conversion.
- The app currently registers the LineageOS `LatinIME` as its only input-method service. Temporary compatibility adapters bridge selected FrostKeys/HeliBoard types while the migration is completed; they do not provide a second IME engine.

Upstream repository: https://github.com/LineageOS/android_packages_inputmethods_LatinIME
