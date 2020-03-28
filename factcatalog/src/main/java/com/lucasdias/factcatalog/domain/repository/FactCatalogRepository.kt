package com.lucasdias.factcatalog.domain.repository

import androidx.lifecycle.LiveData
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.sealedclass.RequestStatus

internal interface FactCatalogRepository {
    suspend fun searchFactsBySubjectFromApi(subject: String): RequestStatus
    fun getAllFacts(): LiveData<List<Fact>>
}
