package com.conamobile.moveviewtoopenkeyboard

import android.view.View
import android.view.Window
import androidx.core.view.*

class InsetsWithKeyboardCallback(window: Window) : OnApplyWindowInsetsListener,
    WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {

    private var deferredInsets = false
    private var view: View? = null
    private var lastWindowInsets: WindowInsetsCompat? = null

    init {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        view = v
        lastWindowInsets = insets
        val types = when {
            deferredInsets -> WindowInsetsCompat.Type.systemBars()
            else -> WindowInsetsCompat.Type.systemBars() + WindowInsetsCompat.Type.ime()
        }
        val typeInsets = insets.getInsets(types)
        v.setPadding(typeInsets.left, typeInsets.top, typeInsets.right, typeInsets.bottom)
        return WindowInsetsCompat.CONSUMED
    }

    override fun onPrepare(animation: WindowInsetsAnimationCompat) {
        if (animation.typeMask and WindowInsetsCompat.Type.ime() != 0) {
            deferredInsets = true
        }
    }

    override fun onProgress(
        insets: WindowInsetsCompat,
        runningAnimations: MutableList<WindowInsetsAnimationCompat>,
    ): WindowInsetsCompat {
        return insets
    }

    override fun onEnd(animation: WindowInsetsAnimationCompat) {
        if (deferredInsets && (animation.typeMask and WindowInsetsCompat.Type.ime()) != 0) {
            deferredInsets = false
            if (lastWindowInsets != null && view != null) {
                ViewCompat.dispatchApplyWindowInsets(view!!, lastWindowInsets!!)
            }
        }
    }

}