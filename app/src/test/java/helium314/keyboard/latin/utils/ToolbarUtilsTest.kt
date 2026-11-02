// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import helium314.keyboard.latin.common.Constants.Separators
import helium314.keyboard.latin.settings.Defaults
import helium314.keyboard.latin.settings.Settings
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ToolbarUtilsTest {
    private lateinit var prefs: SharedPreferences

    @BeforeTest
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        prefs = context.getSharedPreferences("toolbar-utils-test", Context.MODE_PRIVATE)
        prefs.edit { clear() }
    }

    @Test
    fun persistentToolbarKeyFallsBackToVoiceForInvalidValue() {
        prefs.edit { putString(Settings.PREF_PERSISTENT_TOOLBAR_KEY, "NOT_A_TOOLBAR_KEY") }

        assertEquals(ToolbarKey.VOICE, getPersistentToolbarKey(prefs))
        assertEquals(Defaults.PREF_PERSISTENT_TOOLBAR_KEY, prefs.getString(Settings.PREF_PERSISTENT_TOOLBAR_KEY, null))
    }

    @Test
    fun upgradeToolbarPrefsDropsRemovedFeatureKeys() {
        prefs.edit {
            putString(Settings.PREF_TOOLBAR_KEYS, "SETTINGS,true|AI_TOOLS,true|GIFS,true|STICKERS,true|CLIPBOARD,false")
            putString(Settings.PREF_PINNED_TOOLBAR_KEYS, "VOICE,true|AI_TOOLS,true|GIFS,true|STICKERS,true")
        }
        upgradeToolbarPrefs(prefs)
        val toolbar = prefs.getString(Settings.PREF_TOOLBAR_KEYS, "")!!
        val pinned = prefs.getString(Settings.PREF_PINNED_TOOLBAR_KEYS, "")!!
        assertFalse(toolbar.contains("AI_TOOLS"))
        assertFalse(toolbar.contains("GIFS"))
        assertFalse(toolbar.contains("STICKERS"))
        assertFalse(pinned.contains("AI_TOOLS"))
        assertFalse(pinned.contains("GIFS"))
        assertFalse(pinned.contains("STICKERS"))
    }
    @Test
    fun pinnedToolbarKeysAreLimitedToFive() {
        val enabled = listOf(
            ToolbarKey.VOICE,
            ToolbarKey.CLIPBOARD,
            ToolbarKey.NUMPAD,
            ToolbarKey.UNDO,
            ToolbarKey.REDO,
            ToolbarKey.SETTINGS,
        )
        prefs.edit { putString(Settings.PREF_PINNED_TOOLBAR_KEYS, pinnedPref(enabled)) }

        assertEquals(enabled.take(MAX_PINNED_TOOLBAR_KEYS), getPinnedToolbarKeys(prefs))
    }

    @Test
    fun persistentToolbarKeyIsExcludedFromMiddlePinnedKeys() {
        val enabled = listOf(
            ToolbarKey.VOICE,
            ToolbarKey.CLIPBOARD,
            ToolbarKey.NUMPAD,
            ToolbarKey.UNDO,
            ToolbarKey.REDO,
            ToolbarKey.SETTINGS,
        )
        prefs.edit { putString(Settings.PREF_PINNED_TOOLBAR_KEYS, pinnedPref(enabled)) }

        assertEquals(
            listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.UNDO, ToolbarKey.REDO, ToolbarKey.SETTINGS),
            getPinnedToolbarKeys(prefs, ToolbarKey.VOICE)
        )
    }

    @Test
    fun quickPinRefusesSixthMiddlePinnedKey() {
        val enabled = listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.UNDO, ToolbarKey.REDO, ToolbarKey.SETTINGS)
        prefs.edit {
            putString(Settings.PREF_PERSISTENT_TOOLBAR_KEY, ToolbarKey.VOICE.name)
            putString(Settings.PREF_PINNED_TOOLBAR_KEYS, pinnedPref(enabled))
        }

        assertFalse(addPinnedKey(prefs, ToolbarKey.EMOJI))
        assertEquals(enabled, getPinnedToolbarKeys(prefs, ToolbarKey.VOICE))
    }

    @Test
    fun quickPinRefusesPersistentToolbarKeyDuplicate() {
        prefs.edit { putString(Settings.PREF_PERSISTENT_TOOLBAR_KEY, ToolbarKey.EMOJI.name) }

        assertFalse(addPinnedKey(prefs, ToolbarKey.EMOJI))
        assertTrue(getPinnedToolbarKeys(prefs, ToolbarKey.EMOJI).isEmpty())
    }

    @Test
    fun dragPinFromAccessInsertsAtIndexAndPushesOverflow() {
        val enabled = listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.UNDO, ToolbarKey.REDO, ToolbarKey.SETTINGS)
        prefs.edit {
            putString(Settings.PREF_PERSISTENT_TOOLBAR_KEY, ToolbarKey.VOICE.name)
            putString(Settings.PREF_PINNED_TOOLBAR_KEYS, pinnedPref(enabled))
        }

        assertEquals(
            listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.EMOJI, ToolbarKey.UNDO, ToolbarKey.REDO),
            pinToolbarKeyAt(prefs, ToolbarKey.EMOJI, 2)
        )
    }

    @Test
    fun dragPinFromAccessAppendsWhenDroppedPastShortRow() {
        val enabled = listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.UNDO)
        prefs.edit { putString(Settings.PREF_PINNED_TOOLBAR_KEYS, pinnedPref(enabled)) }

        assertEquals(
            listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.UNDO, ToolbarKey.EMOJI),
            pinToolbarKeyAt(prefs, ToolbarKey.EMOJI, 99)
        )
    }

    @Test
    fun dragPinnedKeyMovesExistingKeyWithoutDuplicating() {
        val enabled = listOf(ToolbarKey.CLIPBOARD, ToolbarKey.NUMPAD, ToolbarKey.UNDO, ToolbarKey.REDO)
        prefs.edit { putString(Settings.PREF_PINNED_TOOLBAR_KEYS, pinnedPref(enabled)) }

        assertEquals(
            listOf(ToolbarKey.NUMPAD, ToolbarKey.UNDO, ToolbarKey.CLIPBOARD, ToolbarKey.REDO),
            pinToolbarKeyAt(prefs, ToolbarKey.CLIPBOARD, 2)
        )
    }

    private fun pinnedPref(enabled: List<ToolbarKey>): String {
        return ToolbarKey.entries
            .filterNot { it == ToolbarKey.CLOSE_HISTORY }
            .joinToString(Separators.ENTRY) { it.name + Separators.KV + (it in enabled) }
    }
}
