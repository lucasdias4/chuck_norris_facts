package com.lucasdias.search.data.historic.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.lucasdias.base.typeconverter.TypeConverter
import java.util.*
import kotlin.collections.ArrayList

internal class SearchHistoricCache(private var context: Context) {

    private companion object {
        const val SEARCH_HISTORIC_KEY = "SEARCH_HISTORIC_KEY"
        const val EMPTY_STRING = ""
        const val DO_NOT_CONTAIN = -1
        const val FIRST_POSITION = 0
    }

    fun setSearch(search: String) {

        val historic = getHistoric()
        val indexOfContainedString = historic.indexOf(search.trim())
        if (indexOfContainedString != DO_NOT_CONTAIN) historic.removeAt(indexOfContainedString)

        historic.add(FIRST_POSITION, search)

        val historicAsString = TypeConverter.arrayListToString(historic)

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SEARCH_HISTORIC_KEY, historicAsString)
        editor.apply()
    }

    fun getHistoric(): ArrayList<String> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val historicAsString = preferences.getString(SEARCH_HISTORIC_KEY, EMPTY_STRING)
        val historic = TypeConverter.stringToArrayList(historicAsString) ?: ArrayList()
        return historic
    }
}
