package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.domain.usecase.BaseUseCase
import com.lucasdias.search.domain.repository.SearchHistoricRepository

internal class GetSearchHistoric(
    private val searchHistoricRepository: SearchHistoricRepository
) : BaseUseCase<Unit?, ArrayList<String>> {

    override operator fun invoke(parameter: Unit?) =
            searchHistoricRepository.getHistoric() as ArrayList<String>
}
