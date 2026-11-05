/*
 * Copyright (C) 2026 FrostKeys contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.inputmethod.keyboard;

import android.graphics.Rect;

/**
 * Geometry needed by {@link ProximityInfo} to build native touch-proximity data.
 *
 * <p>This small contract lets the temporary FrostKeys/Heli compatibility path pass its existing
 * key objects through unchanged. The active IME remains the LineageOS LatinIME service.</p>
 */
public interface ProximityInfoKey {
    int getCode();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    boolean isSpacer();
    Rect getHitBox();
    int squaredDistanceToEdge(int x, int y);
}
