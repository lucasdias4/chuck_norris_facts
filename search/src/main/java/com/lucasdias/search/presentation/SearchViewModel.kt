package com.lucasdias.search.presentation

import android.view.KeyEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.Success
import com.lucasdias.search.domain.usecase.GetRandomCategoriesFromDatabase
import com.lucasdias.search.domain.usecase.GetSearchHistoric
import com.lucasdias.search.domain.usecase.IsCategoryCacheEmpty
import com.lucasdias.search.domain.usecase.SearchCategoriesFromApi
import com.lucasdias.search.domain.usecase.SetSearchHistoric
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

internal class SearchViewModel(
    internal val coroutineContext: CoroutineDispatcher,
    internal val getSearchHistoric: GetSearchHistoric,
    internal val setSearchHistoric: SetSearchHistoric,
    internal val searchCategoriesFromApi: SearchCategoriesFromApi,
    internal val getRandomCategoriesFromDatabase: GetRandomCategoriesFromDatabase,
    internal val isCategoryCacheEmpty: IsCategoryCacheEmpty
) : ViewModel() {

    private companion object {
        const val SEARCH_MINIMUM_SIZE = 3
    }

    /**
     * Variáveis como var e internal, por conta dos testes unitários
     **/
    internal var randomCategories = MutableLiveData<List<String>?>()
    internal var errorToLoadCategories = MutableLiveData<Unit>()
    internal var showSuggestionAndHistoricViews = MutableLiveData<Unit>()
    private var searchMustBeLongerThanTwoCharacters = MutableLiveData<Unit>()
    private var doASearch = MutableLiveData<String>()

    fun getHistoric() = getSearchHistoric()
    fun getRandomCategories() = randomCategories
    fun errorToLoadCategories() = errorToLoadCategories
    fun showSuggestionAndHistoricViews() = showSuggestionAndHistoricViews
    fun searchMustBeLongerThanTwoCharacters() = searchMustBeLongerThanTwoCharacters
    fun doASearch() = doASearch

    fun searchCategories() {
        viewModelScope.launch(coroutineContext) {
            var categories: List<String>? = null
            var requestStatus: RequestStatus = Success()

            val categoryCacheIsEmpty = isCategoryCacheEmpty()
            if (categoryCacheIsEmpty) requestStatus = searchCategoriesFromApi()

            showSuggestionAndHistoricViews.postValue(Unit)

            if (requestStatus is Success) categories = getRandomCategoriesFromDatabase()
            else errorToLoadCategories.postValue(Unit)
            if (categories.isNullOrEmpty().not()) randomCategories.postValue(categories)
        }
    }

    fun setSearch(search: String) {
        if (search.length < SEARCH_MINIMUM_SIZE) return
        setSearchHistoric(search = search)
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
            userWantsToSearch(search = searchText)
        }
    }
}
