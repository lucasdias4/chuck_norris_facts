package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.domain.BaseUseCase
import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class GetSearchHistoric(
    private val searchHistoricRepository: SearchHistoricRepository
) : BaseUseCase<None, ArrayList<String>>() {

    override operator fun invoke(parameter: None) = searchHistoricRepository.getHistoric() as ArrayList<String>
}
