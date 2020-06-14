package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.domain.usecase.BaseUseCase
import com.lucasdias.search.domain.repository.CategoryRepository

internal class IsCategoryCacheEmpty(
    private val categoryRepository: CategoryRepository
) : BaseUseCase<Unit?, Boolean> {

    override operator fun invoke(parameter: Unit?) = categoryRepository.isCategoryCacheEmpty()
}
