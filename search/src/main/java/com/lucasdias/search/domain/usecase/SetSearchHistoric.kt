package com.lucasdias.search.domain.usecase

import com.lucasdias.search.domain.repository.SearchHistoricRepository

class SetSearchHistoric(private val searchHistoricRepository: SearchHistoricRepository) {
    operator fun invoke(search: String) {
        searchHistoricRepository.setSearch(search)
    }
}
