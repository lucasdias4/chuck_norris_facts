package com.lucasdias.home.data.userfirsttime.local

import android.content.SharedPreferences

class UserFirstTimeCache(private val sharedPreferences: SharedPreferences) {

    fun setIsNotFirstTime() {
        val isNotFirstTime = false
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_USER_FIRST_TIME, isNotFirstTime)
        editor.apply()
    }

    fun getIfIsUserFirstTime(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_FIRST_TIME, DEFAULT_VALUE)
    }

    private companion object {
        private const val IS_USER_FIRST_TIME = "IS_USER_FIRST_TIME"
        private const val DEFAULT_VALUE = true
    }
}
