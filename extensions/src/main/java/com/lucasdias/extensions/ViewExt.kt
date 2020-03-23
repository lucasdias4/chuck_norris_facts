package com.lucasdias.extensions

import android.view.View
import androidx.annotation.IdRes

fun <T : View> bind(view: View, @IdRes id: Int): Lazy<T> {
    return lazy { view.findViewById<T>(id) }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
