package com.lucasdias.factcatalog.domain.repository

import androidx.lifecycle.LiveData
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.factcatalog.domain.model.Fact

internal interface FactCatalogRepository {
    suspend fun searchFactsBySubjectFromApi(subject: String): RequestStatus
    fun getAllFacts(): LiveData<List<Fact>>
    fun deleteAllFacts()
}
