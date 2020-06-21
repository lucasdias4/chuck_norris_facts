package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.domain.usecase.SuspendableUseCase
import com.lucasdias.search.domain.repository.CategoryRepository

internal class SearchCategoriesFromApi(
    private val categoryRepository: CategoryRepository
) : SuspendableUseCase<Unit?, RequestStatus> {

    override suspend operator fun invoke(parameter: Unit?) = categoryRepository.fetch()
}
