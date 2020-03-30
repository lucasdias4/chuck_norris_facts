package com.lucasdias.search.presentation

import android.view.KeyEvent
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

    private companion object {
        const val SEARCH_MINIMUM_SIZE = 3
    }

    private var coroutineContext = Dispatchers.IO

    private var randomCategoriesLiveData = MutableLiveData<List<String>?>()
    private var errorToLoadCategories = MutableLiveData<Unit>()
    private var showSuggestionAndHistoricViews = MutableLiveData<Unit>()
    private var searchMustBeLongerThanTwoCharacters = MutableLiveData<Unit>()
    private var doASearch = MutableLiveData<String>()

    fun getHistoric() = getSearchHistoric()
    fun getRandomCategories() = randomCategoriesLiveData
    fun errorToLoadCategories() = errorToLoadCategories
    fun showSuggestionAndHistoricViews() = showSuggestionAndHistoricViews
    fun searchMustBeLongerThanTwoCharacters() = searchMustBeLongerThanTwoCharacters
    fun doASearch() = doASearch

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
        if (search.length < SEARCH_MINIMUM_SIZE) return
        setSearchHistoric(search)
    }

    fun userWantsToSearch(search: String?) {
        search?.let {
            if (it.length < SEARCH_MINIMUM_SIZE) {
                searchMustBeLongerThanTwoCharacters.postValue(Unit)
                return
            }
            doASearch.postValue(search)
        }
    }

    fun inputTextKeyboardHandler(event: KeyEvent?, keyCode: Int, searchText: String) {
        val userHitEnterButton =
            (event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)

        if (userHitEnterButton) {
            userWantsToSearch(searchText)
        }
    }
}
