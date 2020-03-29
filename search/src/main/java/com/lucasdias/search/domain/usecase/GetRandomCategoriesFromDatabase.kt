package com.lucasdias.search.domain.usecase

import com.lucasdias.search.domain.repository.CategoryRepository

class GetRandomCategoriesFromDatabase(private val categoryRepository: CategoryRepository) {

    private companion object {
        const val FIRST_ITEM = 0
        const val LAST_ITEM = 8
    }

    operator fun invoke(): List<String>? {
        val categories = categoryRepository.getCategories()
        val shuffledCategories = categories?.shuffled()
        return shuffledCategories?.subList(FIRST_ITEM, LAST_ITEM)
    }
}
