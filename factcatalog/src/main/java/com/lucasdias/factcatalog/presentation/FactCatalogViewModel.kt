package com.lucasdias.factcatalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FactCatalogViewModel(
    private val searchFactsBySubjectFromApi: SearchFactsBySubjectFromApi
) : ViewModel() {

    private var coroutineContext = Dispatchers.IO
    private var factsLiveData = MutableLiveData<FactListResponse>()

    fun updateFactsLiveData(): LiveData<FactListResponse> = factsLiveData

    fun searchFactsBySubject(subject: String) {
        CoroutineScope(coroutineContext).launch {
            val result = searchFactsBySubjectFromApi.invoke(subject = subject)
            factsLiveData.postValue(result)
        }
    }
}
