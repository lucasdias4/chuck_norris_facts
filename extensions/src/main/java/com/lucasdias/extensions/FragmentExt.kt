package com.lucasdias.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun <T : View> Fragment.bind(@IdRes id: Int): Lazy<T?> {
    return lazy { view?.findViewById<T>(id) }
}
