package com.lucasdias.search.data

import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class SearchHistoricRepositoryImpl : SearchHistoricRepository {

    override fun getHistoric() = SearchHistoricCache.getHistoric()

    override fun setSearch(search: String) {
        SearchHistoricCache.setSearch(search)
    }
}
