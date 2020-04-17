package com.lucasdias.factcatalog

import android.view.View
import android.widget.TextView
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.presentation.FactCatalogAdapter
import com.lucasdias.factcatalog.presentation.FactCatalogAdapter.Companion.UNCATEGORIZED
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class FactCatalogAdapter {

    private val view: View = mockk()
    private val categoryTextView: TextView = mockk()
    private val viewHolder = spyk(FactCatalogAdapter.ViewHolder(view))
    private val listOfCategory = mutableListOf<String>()

    @Before
    fun setUp() {
        listOfCategory.add(ANY_CATEGORY_LOWER_CASE)
    }

    @Test
    fun `IF a fact in the list has a value below the letter limit THEN the letters will be large`() {
        val fact = Fact(
            id = FILLED_STRING,
            value = STRING_BELOW_THE_LETTER_LIMIT,
            url = FILLED_STRING,
            categories = listOfCategory
        )

        every {
            categoryTextView.setTextSize(any(), any())
        } just Runs

        every {
            categoryTextView.text = any()
        } just Runs

        viewHolder.contentSetup(fact = fact, content = categoryTextView)

        verify(exactly = 1) {
            categoryTextView.setTextSize(any(), FactCatalogAdapter.BIG_TEXT_SIZE)
        }
    }

    @Test
    fun `IF a fact in the list has a value equal to the letter limit THEN the letters will be small`() {
        val fact = Fact(
            id = FILLED_STRING,
            value = STRING_WITH_THE_LIMIT_OF_LETTERS,
            url = FILLED_STRING,
            categories = listOfCategory
        )

        every {
            categoryTextView.setTextSize(any(), any())
        } just Runs

        every {
            categoryTextView.text = any()
        } just Runs

        viewHolder.contentSetup(fact = fact, content = categoryTextView)

        verify(exactly = 1) {
            categoryTextView.setTextSize(any(), FactCatalogAdapter.SMALL_TEXT_SIZE)
        }
    }

    @Test
    fun `IF a fact in the list has a value over to the letter limit THEN the letters will be small`() {
        val fact = Fact(
            id = FILLED_STRING,
            value = STRING_OVER_THE_LETTER_LIMIT,
            url = FILLED_STRING,
            categories = listOfCategory
        )

        every {
            categoryTextView.setTextSize(any(), any())
        } just Runs

        every {
            categoryTextView.text = any()
        } just Runs

        viewHolder.contentSetup(fact = fact, content = categoryTextView)

        verify(exactly = 1) {
            categoryTextView.setTextSize(any(), FactCatalogAdapter.SMALL_TEXT_SIZE)
        }
    }

    @Test
    fun `IF a fact has no category THEN the app will show a tag uncategorized as its category`() {
        val fact = spyk(
            Fact(
                id = FILLED_STRING,
                value = FILLED_STRING,
                url = FILLED_STRING,
                categories = null
            )
        )
        every { categoryTextView.text = UNCATEGORIZED } just Runs

        viewHolder.categorySetup(fact = fact, categoryTextView = categoryTextView)

        verify(exactly = 1) { categoryTextView.text = UNCATEGORIZED }
    }

    @Test
    fun `IF a fact has a category THEN the app will show its category as a tag`() {

        val fact = spyk(
            Fact(
                id = FILLED_STRING,
                value = FILLED_STRING,
                url = FILLED_STRING,
                categories = listOfCategory
            )
        )
        every { categoryTextView.text = any() } just Runs

        viewHolder.categorySetup(fact = fact, categoryTextView = categoryTextView)

        verify(exactly = 1) { categoryTextView.text = ANY_CATEGORY_LOWER_CASE }
    }

    private companion object {
        const val STRING_BELOW_THE_LETTER_LIMIT =
            "rhqNhkQFFDWxJqxFkD1ar1shjLC9oLGGX9q1q46TJ3sIRWTnZu03YKGViY7BLgcKiGlRa2IFInu4NzK"
        const val STRING_WITH_THE_LIMIT_OF_LETTERS =
            "rhqNhkQFFDWxJqxFkD1ar1shjLC9oLGGX9q1q46TJ3sIRWTnZu03YKGViY7BLgcKiGlRa2IFInu4NzK6"
        const val STRING_OVER_THE_LETTER_LIMIT =
            "rhqNhkQFFDWxJqxFkD1ar1shjLC9oLGGX9q1q46TJ3sIRWTnZu03YKGViY7BLgcKiGlRa2IFInu4NzK6_"
        const val FILLED_STRING = "ABCD"
        const val ANY_CATEGORY_LOWER_CASE = "any_category_lower_case"
    }
}
