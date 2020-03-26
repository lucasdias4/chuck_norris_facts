package com.lucasdias.search.domain.usecase

import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class GetSearchHistoric(private val searchHistoricRepository: SearchHistoricRepository) {
    operator fun invoke() = searchHistoricRepository.getHistoric()
}
