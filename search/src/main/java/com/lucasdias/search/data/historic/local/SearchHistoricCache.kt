package com.lucasdias.search.data.historic.local

import android.content.SharedPreferences

internal class SearchHistoricCache(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val SEARCH_HISTORIC_KEY = "SEARCH_HISTORIC_KEY"
        const val EMPTY_STRING = ""
    }

    fun setHistoricAsString(historicAsString: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(SEARCH_HISTORIC_KEY, historicAsString)
        editor.apply()
    }

    fun getHistoricAsString(): String? {
        val historicAsString = sharedPreferences.getString(SEARCH_HISTORIC_KEY, EMPTY_STRING)
        return historicAsString
    }
}
