package com.lucasdias.bottomnavigation.model

import android.graphics.drawable.Drawable

data class BottomNavigationOption(
    var id: Int,
    var title: String,
    var icon: Drawable,
    var order: Int,
    var startFragment: () -> Unit
)
