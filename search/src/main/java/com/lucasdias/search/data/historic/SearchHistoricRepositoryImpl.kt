package com.lucasdias.search.data.historic

import com.lucasdias.search.data.historic.local.SearchHistoricCache
import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class SearchHistoricRepositoryImpl(
    private val searchHistoricCache: SearchHistoricCache
) : SearchHistoricRepository {

    internal companion object {
        const val DOES_NOT_CONTAIN = -1
        const val FIRST_POSITION = 0
        const val DELIMITERS = ","
    }

    override fun setSearch(search: String) {
        val historic = getHistoric()
        val indexOfContainedString = historic.indexOf(search.trim())
        if (indexOfContainedString != DOES_NOT_CONTAIN) historic.removeAt(indexOfContainedString)
        historic.add(FIRST_POSITION, search)

        val historicAsString = historic.joinToString()
        searchHistoricCache.setHistoricAsString(historicAsString = historicAsString)
    }

    override fun getHistoric(): ArrayList<String> {
        val historic = ArrayList<String>()
        val historicAsString = searchHistoricCache.getHistoricAsString()
        val historicAsList = historicAsString?.split(DELIMITERS)
        historicAsList?.map { search ->
            historic.add(search.trim())
        }
        return historic
    }
}
