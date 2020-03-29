package com.lucasdias.search.domain.usecase

import com.lucasdias.search.domain.repository.CategoryRepository

internal class SearchCategoriesFromApi(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke() = categoryRepository.searchCategoriesFromApi()
}
