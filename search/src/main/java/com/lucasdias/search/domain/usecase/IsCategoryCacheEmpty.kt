package com.lucasdias.search.domain.usecase

import com.lucasdias.search.domain.repository.CategoryRepository

internal class IsCategoryCacheEmpty(private val categoryRepository: CategoryRepository) {
    operator fun invoke() = categoryRepository.isCategoryCacheEmpty()
}
