/* SPDX-License-Identifier: Apache-2.0 AND GPL-3.0-only */
package com.android.inputmethod.latin;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * A dependency-neutral hook for app-provided IME window effects.
 *
 * The Lineage engine module must not depend on app-layer FrostKeys classes. The app may install a
 * controller, while the active IME reports view/window lifecycle changes through this bridge.
 */
public final class ImeWindowEffectBridge {
    public interface Controller {
        boolean isEffectEnabled(Context context);
        void apply(InputMethodService service, View inputView, boolean enabled);
    }

    private static volatile Controller sController;
    private static WeakReference<InputMethodService> sActiveService = new WeakReference<>(null);
    private static WeakReference<View> sActiveInputView = new WeakReference<>(null);

    private ImeWindowEffectBridge() {}

    public static void setController(final Controller controller) {
        sController = controller;
        refreshActive();
    }

    public static void onInputViewChanged(final InputMethodService service, final View inputView) {
        synchronized (ImeWindowEffectBridge.class) {
            sActiveService = new WeakReference<>(service);
            sActiveInputView = new WeakReference<>(inputView);
        }
        refresh(service, inputView);
    }

    public static void refresh(final InputMethodService service, final View inputView) {
        final Controller controller = sController;
        if (controller == null) return;
        final boolean enabled = service.isInputViewShown() && controller.isEffectEnabled(service);
        controller.apply(service, inputView, enabled);
    }

    public static void disable(final InputMethodService service) {
        final Controller controller = sController;
        if (controller == null) return;
        final View inputView;
        synchronized (ImeWindowEffectBridge.class) {
            inputView = sActiveService.get() == service ? sActiveInputView.get() : null;
        }
        controller.apply(service, inputView, false);
    }

    public static void refreshActive() {
        final InputMethodService service;
        final View inputView;
        synchronized (ImeWindowEffectBridge.class) {
            service = sActiveService.get();
            inputView = sActiveInputView.get();
        }
        if (service != null) refresh(service, inputView);
    }

    public static void clear(final InputMethodService service) {
        final Controller controller = sController;
        final View inputView;
        synchronized (ImeWindowEffectBridge.class) {
            if (sActiveService.get() != service) return;
            inputView = sActiveInputView.get();
            sActiveService = new WeakReference<>(null);
            sActiveInputView = new WeakReference<>(null);
        }
        if (controller != null) controller.apply(service, inputView, false);
    }
}
