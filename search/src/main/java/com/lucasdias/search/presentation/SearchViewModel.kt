package com.lucasdias.search.presentation

import androidx.lifecycle.ViewModel
import com.lucasdias.search.domain.usecase.GetSearchHistoric
import com.lucasdias.search.domain.usecase.SetSearchHistoric

internal class SearchViewModel(
    private val getSearchHistoric: GetSearchHistoric,
    private val setSearchHistoric: SetSearchHistoric
) : ViewModel() {

    fun getHistoric() = getSearchHistoric()

    fun setSearch(search: String) {
        if (search.isEmpty()) return
        setSearchHistoric(search)
    }
}
