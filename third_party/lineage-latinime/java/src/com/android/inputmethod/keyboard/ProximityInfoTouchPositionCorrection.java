/*
 * Copyright (C) 2026 FrostKeys contributors
 * SPDX-License-Identifier: Apache-2.0 AND GPL-3.0-only
 */
package com.android.inputmethod.keyboard;

/** Read-only touch-position correction data consumed by the proximity grid. */
public interface ProximityInfoTouchPositionCorrection {
    boolean isValid();

    int getRows();

    float getX(int row);

    float getY(int row);

    float getRadius(int row);
}
