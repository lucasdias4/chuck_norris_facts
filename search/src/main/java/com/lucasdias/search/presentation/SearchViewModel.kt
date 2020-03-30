package com.lucasdias.search.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasdias.search.domain.sealedclass.RequestStatus
import com.lucasdias.search.domain.sealedclass.Success
import com.lucasdias.search.domain.usecase.GetRandomCategoriesFromDatabase
import com.lucasdias.search.domain.usecase.GetSearchHistoric
import com.lucasdias.search.domain.usecase.IsCategoryCacheEmpty
import com.lucasdias.search.domain.usecase.SearchCategoriesFromApi
import com.lucasdias.search.domain.usecase.SetSearchHistoric
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val getSearchHistoric: GetSearchHistoric,
    private val setSearchHistoric: SetSearchHistoric,
    private val searchCategoriesFromApi: SearchCategoriesFromApi,
    private val getRandomCategoriesFromDatabase: GetRandomCategoriesFromDatabase,
    private val isCategoryCacheEmpty: IsCategoryCacheEmpty
) : ViewModel() {

    private var coroutineContext = Dispatchers.IO

    private var randomCategoriesLiveData = MutableLiveData<List<String>?>()
    private var errorToLoadCategories = MutableLiveData<Unit>()
    private var showSuggestionAndHistoricViews = MutableLiveData<Unit>()

    fun getHistoric() = getSearchHistoric()
    fun getRandomCategories() = randomCategoriesLiveData
    fun errorToLoadCategories() = errorToLoadCategories
    fun showSuggestionAndHistoricViews() = showSuggestionAndHistoricViews

    fun searchCategories() {
        CoroutineScope(coroutineContext).launch {
            var categories: List<String>? = null
            var requestStatus: RequestStatus = Success

            val categoryCacheIsEmpty = isCategoryCacheEmpty()

            if (categoryCacheIsEmpty) {
                requestStatus = searchCategoriesFromApi()
            }

            showSuggestionAndHistoricViews.postValue(Unit)

            if (requestStatus == Success) categories = getRandomCategoriesFromDatabase()
            else errorToLoadCategories.postValue(Unit)
            if (categories.isNullOrEmpty().not()) randomCategoriesLiveData.postValue(categories)
        }
    }

    fun setSearch(search: String) {
        if (search.isEmpty()) return
        setSearchHistoric(search)
    }
}
