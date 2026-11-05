// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.utils

import com.android.inputmethod.latin.BinaryDictionary
import java.io.File
import java.util.WeakHashMap

/** Computes the same local SHA-256 fingerprint used by FrostKeys' gesture-data allowlist. */
object DictionaryHash {
    private val cache = WeakHashMap<BinaryDictionary, String>()

    @JvmStatic
    fun getHash(dictionary: BinaryDictionary): String = synchronized(cache) {
        cache[dictionary] ?: ChecksumCalculator.checksum(File(dictionary.dictionaryFilePath))
            .orEmpty()
            .also { cache[dictionary] = it }
    }
}
