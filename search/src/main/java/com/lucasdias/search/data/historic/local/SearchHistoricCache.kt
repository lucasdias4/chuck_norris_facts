package com.lucasdias.search.data.historic.local

import android.content.Context
import androidx.preference.PreferenceManager

internal class SearchHistoricCache(private var context: Context) {

    private companion object {
        const val SEARCH_HISTORIC_KEY = "SEARCH_HISTORIC_KEY"
        const val EMPTY_STRING = ""
    }

    fun setHistoricAsString(historicAsString: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SEARCH_HISTORIC_KEY, historicAsString)
        editor.apply()
    }

    fun getHistoricAsString(): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val historicAsString = preferences.getString(SEARCH_HISTORIC_KEY, EMPTY_STRING)
        return historicAsString
    }
}
