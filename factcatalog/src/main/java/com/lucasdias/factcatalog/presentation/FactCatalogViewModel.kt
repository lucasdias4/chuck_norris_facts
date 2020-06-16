package com.lucasdias.factcatalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.presentation.BaseViewModel
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.usecase.DeleteAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.GetAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import kotlinx.coroutines.CoroutineDispatcher

internal class FactCatalogViewModel(
    internal val getAllFactsFromDatabase: GetAllFactsFromDatabase,
    private val searchFactsBySubjectFromApi: SearchFactsBySubjectFromApi,
    private val deleteAllFactsFromDatabase: DeleteAllFactsFromDatabase,
    private val coroutineContext: CoroutineDispatcher
) : BaseViewModel(coroutineContext) {

    /**
     * Variáveis como var e internal, por conta dos testes unitários
     **/
    internal var _showAnErrorScreen = MutableLiveData<Unit>()
    internal var _showAnEmptySearchScreen = MutableLiveData<Unit>()
    internal var _turnOnLoading = MutableLiveData<Unit>()
    internal var _turnOffLoading = MutableLiveData<Unit>()
    private var hasNetworkConnectivity = true

    fun showAnErrorScreen(): LiveData<Unit> = _showAnErrorScreen
    fun showAnEmptySearchScreen(): LiveData<Unit> = _showAnEmptySearchScreen
    fun turnOnLoading(): LiveData<Unit> = _turnOnLoading
    fun turnOffLoadingLiveData(): LiveData<Unit> = _turnOffLoading
    fun deleteAllFacts() = deleteAllFactsFromDatabase()
    fun updateFactsLiveData(): LiveData<List<Fact>> = getAllFactsFromDatabase()

    fun searchFactsBySubject(subject: String) {
        if (subject.isEmpty()) return
        if (hasNetworkConnectivity.not()) return

        launch {
            _turnOnLoading.postValue(Unit)
            val requestStatus = searchFactsBySubjectFromApi.invoke(subject)
            requestStatusHandler(requestStatus = requestStatus)
            _turnOffLoading.postValue(Unit)
        }
    }

    private fun requestStatusHandler(requestStatus: RequestStatus) {
        when (requestStatus) {

            is RequestStatus.Success -> {}
            is RequestStatus.SuccessWithoutData -> {
                _showAnEmptySearchScreen.postValue(Unit)
            }
            else -> {
                _showAnErrorScreen.postValue(Unit)
            }
        }
    }

    fun updateConnectivityStatus(hasNetworkConnectivity: Boolean) {
        this.hasNetworkConnectivity = hasNetworkConnectivity
    }
}
