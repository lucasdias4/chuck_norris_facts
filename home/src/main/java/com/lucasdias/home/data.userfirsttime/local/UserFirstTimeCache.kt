package com.lucasdias.home.data.userfirsttime.local

import android.content.Context
import androidx.preference.PreferenceManager

class UserFirstTimeCache {

    private companion object {
        private const val IS_USER_FIRST_TIME = "IS_USER_FIRST_TIME"
        private const val DEFAULT_VALUE = true
    }

    fun setIsNotFirstTime(context: Context) {
        val isNotFirstTime = false
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putBoolean(IS_USER_FIRST_TIME, isNotFirstTime)
        editor.apply()
    }

    fun getIfIsUserFirstTime(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(IS_USER_FIRST_TIME, DEFAULT_VALUE)
    }
}
