package com.lucasdias.search.domain.usecase

import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.search.domain.repository.CategoryRepository
import com.lucasdias.search.domain.usecase.GetRandomCategoriesFromDatabase.Companion.LAST_ITEM
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetRandomCategoriesFromDatabaseTest {

    private val categoryRepository: CategoryRepository = mockk()
    private val getRandomCategoriesFromDatabase = spyk(GetRandomCategoriesFromDatabase(categoryRepository))
    private val categories = spyk(ArrayList<String>())

    @Before
    fun setUp() {
        categories.add(FIRST_CATEGORY)
        categories.add(SECOND_CATEGORY)
        categories.add(THIRD_CATEGORY)
        categories.add(FOURTH_CATEGORY)
        categories.add(FIFTH_CATEGORY)
        categories.add(SIXTH_CATEGORY)
        categories.add(SEVENTH_CATEGORY)
        categories.add(EIGHTH_CATEGORY)
    }

    @Test
    fun `IF the use case was called THEN calls the getCategories method to get categories from the repository`() {
        every {
            categoryRepository.getCategories()
        } returns categories

        getRandomCategoriesFromDatabase.invoke(None())

        verify(exactly = 1) { categoryRepository.getCategories() }
    }

    @Test
    fun `IF the use case was called THEN scrambles the list of categories returned from the repository`() {
        every {
            categoryRepository.getCategories()
        } returns categories

        getRandomCategoriesFromDatabase.invoke(None())

        verify(exactly = 1) { categories.shuffled() }
    }

    @Test
    fun `IF the use case was called THEN return a sub-list with only eight categories`() {
        every {
            categoryRepository.getCategories()
        } returns categories

        val categories = getRandomCategoriesFromDatabase.invoke(None())

        val expectedCategoriesSize = LAST_ITEM
        val actualCategoriesSize = categories?.size

        assertEquals(expectedCategoriesSize, actualCategoriesSize)
    }

    private companion object {
        const val FIRST_CATEGORY = "FIRST_CATEGORY"
        const val SECOND_CATEGORY = "SECOND_CATEGORY"
        const val THIRD_CATEGORY = "THIRD_CATEGORY"
        const val FOURTH_CATEGORY = "FOURTH_CATEGORY"
        const val FIFTH_CATEGORY = "FIFTH_CATEGORY"
        const val SIXTH_CATEGORY = "SIXTH_CATEGORY"
        const val SEVENTH_CATEGORY = "SEVENTH_CATEGORY"
        const val EIGHTH_CATEGORY = "EIGHTH_CATEGORY"
    }
}
