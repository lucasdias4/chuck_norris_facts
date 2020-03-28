package com.lucasdias.search.data

import com.lucasdias.search.data.local.SearchHistoricCache
import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class SearchHistoricRepositoryImpl(
    private val searchHistoricCache: SearchHistoricCache
) : SearchHistoricRepository {

    override fun getHistoric() = searchHistoricCache.getHistoric()

    override fun setSearch(search: String) {
        searchHistoricCache.setSearch(search)
    }
}
