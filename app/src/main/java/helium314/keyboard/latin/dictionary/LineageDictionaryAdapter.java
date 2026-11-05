/*
 * Copyright (C) 2026 FrostKeys contributors
 * SPDX-License-Identifier: Apache-2.0 AND GPL-3.0-only
 */
package helium314.keyboard.latin.dictionary;

import helium314.keyboard.latin.NgramContext;
import helium314.keyboard.latin.SuggestedWords.SuggestedWordInfo;
import helium314.keyboard.latin.common.ComposedData;
import helium314.keyboard.latin.common.InputPointers;
import helium314.keyboard.latin.settings.SettingsValuesForSuggestion;
import helium314.keyboard.latin.makedict.WordProperty;

import java.util.ArrayList;

/** Bridges legacy FrostKeys settings/dictionary utilities to the LineageOS LatinIME API. */
final class LineageDictionaryAdapter {
    private LineageDictionaryAdapter() {}

    static com.android.inputmethod.latin.NgramContext toLineage(
            final NgramContext context) {
        final int wordCount = context.getPrevWordCount();
        final com.android.inputmethod.latin.NgramContext.WordInfo[] words =
                new com.android.inputmethod.latin.NgramContext.WordInfo[wordCount];
        for (int i = 0; i < wordCount; i++) {
            final int oneBasedIndex = i + 1;
            final CharSequence word = context.getNthPrevWord(oneBasedIndex);
            if (context.isNthPrevWordBeginningOfSentence(oneBasedIndex)) {
                words[i] = com.android.inputmethod.latin.NgramContext.WordInfo
                        .BEGINNING_OF_SENTENCE_WORD_INFO;
            } else if (word == null) {
                words[i] = com.android.inputmethod.latin.NgramContext.WordInfo.EMPTY_WORD_INFO;
            } else {
                words[i] = new com.android.inputmethod.latin.NgramContext.WordInfo(word);
            }
        }
        return new com.android.inputmethod.latin.NgramContext(words);
    }

    static com.android.inputmethod.latin.common.ComposedData toLineage(
            final ComposedData data) {
        final InputPointers source = data.mInputPointers;
        final int pointerCount = source.getPointerSize();
        final com.android.inputmethod.latin.common.InputPointers pointers =
                new com.android.inputmethod.latin.common.InputPointers(pointerCount);
        final int[] xs = source.getXCoordinates();
        final int[] ys = source.getYCoordinates();
        final int[] ids = source.getPointerIds();
        final int[] times = source.getTimes();
        for (int i = 0; i < pointerCount; i++) {
            pointers.addPointer(xs[i], ys[i], ids[i], times[i]);
        }
        return new com.android.inputmethod.latin.common.ComposedData(
                pointers, data.mIsBatchMode, data.mTypedWord);
    }

    static com.android.inputmethod.latin.settings.SettingsValuesForSuggestion toLineage(
            final SettingsValuesForSuggestion settings) {
        return new com.android.inputmethod.latin.settings.SettingsValuesForSuggestion(
                settings.mBlockPotentiallyOffensive);
    }

    static com.android.inputmethod.latin.SuggestedWords.SuggestedWordInfo toLineage(
            final SuggestedWordInfo suggestion) {
        return new com.android.inputmethod.latin.SuggestedWords.SuggestedWordInfo(
                suggestion.mWord, suggestion.mPrevWordsContext, suggestion.mScore,
                suggestion.mKindAndFlags, null, suggestion.mIndexOfTouchPointOfSecondWord,
                suggestion.mAutoCommitFirstWordConfidence);
    }

    static ArrayList<SuggestedWordInfo> toFrostKeys(
            final ArrayList<com.android.inputmethod.latin.SuggestedWords.SuggestedWordInfo>
                    suggestions,
            final Dictionary sourceDictionary) {
        if (suggestions == null) {
            return null;
        }
        final ArrayList<SuggestedWordInfo> converted = new ArrayList<>(suggestions.size());
        for (final com.android.inputmethod.latin.SuggestedWords.SuggestedWordInfo suggestion
                : suggestions) {
            if (suggestion.mApplicationSpecifiedCompletionInfo != null) {
                converted.add(new SuggestedWordInfo(
                        suggestion.mApplicationSpecifiedCompletionInfo));
            } else {
                converted.add(new SuggestedWordInfo(suggestion.mWord,
                        suggestion.mPrevWordsContext, suggestion.mScore,
                        suggestion.mKindAndFlags, sourceDictionary,
                        suggestion.mIndexOfTouchPointOfSecondWord,
                        suggestion.mAutoCommitFirstWordConfidence));
            }
        }
        return converted;
    }

    static WordProperty toFrostKeys(
            final com.android.inputmethod.latin.makedict.WordProperty property) {
        return property == null ? null : WordProperty.fromLineage(property);
    }
}
