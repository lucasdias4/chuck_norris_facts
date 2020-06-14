package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.domain.SuspendableUseCase
import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.search.domain.repository.CategoryRepository

internal class SearchCategoriesFromApi(
    private val categoryRepository: CategoryRepository
) : SuspendableUseCase<None, RequestStatus>() {

    override suspend operator fun invoke(parameter: None)
            = categoryRepository.searchCategoriesFromApi()
}
