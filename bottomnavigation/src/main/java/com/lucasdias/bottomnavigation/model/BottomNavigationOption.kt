package com.lucasdias.bottomnavigation.model

import android.graphics.drawable.Drawable

data class BottomNavigationOption(
    val id: Int,
    val title: String,
    val icon: Drawable,
    val order: Int,
    val startFragment: () -> Unit
)
