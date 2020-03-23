package com.lucasdias.extensions

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

fun <T : View> Activity.bind(@IdRes res: Int): Lazy<T> {
    return lazy { findViewById<T>(res) }
}
