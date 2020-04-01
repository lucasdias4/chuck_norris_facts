package com.lucasdias.search.data.historic

import com.lucasdias.base.typeconverter.TypeConverter
import com.lucasdias.search.data.historic.local.SearchHistoricCache
import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class SearchHistoricRepositoryImpl(
    private val searchHistoricCache: SearchHistoricCache
) : SearchHistoricRepository {

    private companion object {
        const val DO_NOT_CONTAIN = -1
        const val FIRST_POSITION = 0
    }

    override fun setSearch(search: String) {
        val historic = getHistoric()
        val indexOfContainedString = historic.indexOf(search.trim())
        if (indexOfContainedString != DO_NOT_CONTAIN) historic.removeAt(indexOfContainedString)
        historic.add(FIRST_POSITION, search)

        val historicAsString = TypeConverter.arrayListToString(historic)
        searchHistoricCache.setHistoricAsString(historicAsString)
    }

    override fun getHistoric(): ArrayList<String> {
        val historicAsString = searchHistoricCache.getHistoricAsString()
        val historic = TypeConverter.stringToArrayList(historicAsString) ?: ArrayList()
        return historic
    }
}
