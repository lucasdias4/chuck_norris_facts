package com.lucasdias.factcatalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.usecase.GetAllFactsFromDatabase
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FactCatalogViewModel(
    private val searchFactsBySubjectFromApi: SearchFactsBySubjectFromApi,
    private val getAllFactsFromDatabase: GetAllFactsFromDatabase
) : ViewModel() {

    private var coroutineContext = Dispatchers.IO
    private var hasNetworkConnectivity = true

    fun updateFactsLiveData(): LiveData<List<Fact>> = getAllFactsFromDatabase()

    fun searchFactsBySubject(subject: String) {
        if (hasNetworkConnectivity) {
            CoroutineScope(coroutineContext).launch {
                searchFactsBySubjectFromApi.invoke(subject = subject)
            }
        }
    }

    fun updateConnectivityStatus(hasNetworkConnectivity: Boolean) {
        this.hasNetworkConnectivity = hasNetworkConnectivity
    }
}
