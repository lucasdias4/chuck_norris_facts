package com.lucasdias.factcatalog.data.searchtext.local

import android.content.Context
import androidx.preference.PreferenceManager

class SearchTextCache {

    private companion object {
        private const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val DEFAULT_VALUE = ""
    }

    fun setActualSearchText(searchText: String, context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SEARCH_TEXT, searchText)
        editor.apply()
    }

    fun getActualSearchText(context: Context): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SEARCH_TEXT, DEFAULT_VALUE) ?: DEFAULT_VALUE
    }
}
