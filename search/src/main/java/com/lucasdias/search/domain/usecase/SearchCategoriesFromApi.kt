package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.domain.usecase.BaseSuspendableUseCase
import com.lucasdias.search.domain.repository.CategoryRepository

internal class SearchCategoriesFromApi(
    private val categoryRepository: CategoryRepository
) : BaseSuspendableUseCase<Unit?, RequestStatus> {

    override suspend operator fun invoke(parameter: Unit?) = categoryRepository.fetch()
}
