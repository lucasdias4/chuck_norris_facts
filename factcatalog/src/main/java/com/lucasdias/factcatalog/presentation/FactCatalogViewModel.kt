package com.lucasdias.factcatalog.presentation

import androidx.lifecycle.ViewModel
import com.lucasdias.factcatalog.domain.usecase.SearchFactsBySubjectFromApi
import com.lucasdias.log.LogApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FactCatalogViewModel(
    private val searchFactsBySubjectFromApi: SearchFactsBySubjectFromApi
) : ViewModel() {

    private var coroutineContext = Dispatchers.IO

    fun searchFactsBySubject() {
        CoroutineScope(coroutineContext).launch {
            val result = searchFactsBySubjectFromApi.invoke(subject = "water")
            result?.facts?.forEach {
                LogApp.i("Request result", "${it.categories}")
            }
        }
    }
}
