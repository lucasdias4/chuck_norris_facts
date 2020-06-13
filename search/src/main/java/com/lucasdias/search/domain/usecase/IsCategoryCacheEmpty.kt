package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.domain.BaseUseCase
import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.search.domain.repository.CategoryRepository

internal class IsCategoryCacheEmpty(
    private val categoryRepository: CategoryRepository
) : BaseUseCase<None, Boolean>() {

    override operator fun invoke(parameter: None) = categoryRepository.isCategoryCacheEmpty()
}
