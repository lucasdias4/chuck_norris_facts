package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.domain.BaseUseCase
import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.search.domain.repository.CategoryRepository

class GetRandomCategoriesFromDatabase(
    private val categoryRepository: CategoryRepository
) : BaseUseCase<None, List<String>?>() {

    override operator fun invoke(parameter: None): List<String>? {
        val categories = categoryRepository.getCategories()
        val shuffledCategories = categories?.shuffled()
        return shuffledCategories?.subList(FIRST_ITEM, LAST_ITEM)
    }

    internal companion object {
        const val FIRST_ITEM = 0
        const val LAST_ITEM = 8
    }
}
