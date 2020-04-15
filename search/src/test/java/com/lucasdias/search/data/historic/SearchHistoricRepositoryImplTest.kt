package com.lucasdias.search.data.historic

import com.lucasdias.search.data.historic.SearchHistoricRepositoryImpl.Companion.DOES_NOT_CONTAIN
import com.lucasdias.search.data.historic.SearchHistoricRepositoryImpl.Companion.FIRST_POSITION
import com.lucasdias.search.data.historic.local.SearchHistoricCache
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SearchHistoricRepositoryImplTest {

    private val searchHistoricCache: SearchHistoricCache = mockk()
    private val searchHistoricRepositoryImpl =
        spyk(SearchHistoricRepositoryImpl(searchHistoricCache))

    private val historic = spyk(ArrayList<String>())

    @Before
    fun setUp() {
        every { searchHistoricRepositoryImpl.getHistoric() } returns historic
        every { historic.removeAt(any()) } returns FILLED_STRING
        every { searchHistoricCache.setHistoricAsString(any()) } just Runs
        every { historic.add(any(), any()) } just Runs
    }

    @Test
    fun `IF setSearch is called THEN it will always call getHistoric`() {
        every { historic.indexOf(any()) } returns DOES_NOT_CONTAIN

        searchHistoricRepositoryImpl.setSearch(FILLED_STRING)

        verify(exactly = 1) { searchHistoricRepositoryImpl.getHistoric() }
    }

    @Test
    fun `IF the user tries to do a new search THEN it will always try to know if the new search text is already in the historic cache`() {
        every { historic.indexOf(any()) } returns DOES_NOT_CONTAIN

        searchHistoricRepositoryImpl.setSearch(FILLED_STRING)

        verify(exactly = 1) { historic.indexOf(any()) }
    }

    @Test
    fun `IF the user tries to do a new search with text that already exists in the historic cache THEN remove it from the cache`() {
        every { historic.indexOf(any()) } returns INDEX_WHERE_IT_CONTAINS
        every { historic.removeAt(any()) } returns FILLED_STRING

        searchHistoricRepositoryImpl.setSearch(FILLED_STRING)

        verify(exactly = 1) { historic.removeAt(any()) }
    }

    @Test
    fun `IF the user tries to do a new search with text that already exists in the historic cache, it will be removed from the list and THEN it will be inserted again at the top of the list`() {
        every { historic.indexOf(any()) } returns DOES_NOT_CONTAIN

        searchHistoricRepositoryImpl.setSearch(FILLED_STRING)

        verify(exactly = 1) { historic.add(FIRST_POSITION, FILLED_STRING) }
    }

    @Test
    fun `IF setSearch is called THEN it will always call the SearchHistoricCache's setHistoricAsString method`() {
        every { searchHistoricCache.setHistoricAsString(any()) } just Runs

        searchHistoricRepositoryImpl.setSearch(FILLED_STRING)

        verify(exactly = 1) { searchHistoricCache.setHistoricAsString(any()) }
    }

    private companion object {
        const val FILLED_STRING = "ABCD"
        const val INDEX_WHERE_IT_CONTAINS = 1
    }
}
