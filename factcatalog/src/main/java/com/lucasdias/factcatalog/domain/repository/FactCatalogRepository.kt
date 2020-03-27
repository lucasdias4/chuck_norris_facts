package com.lucasdias.factcatalog.domain.repository

import androidx.lifecycle.LiveData
import com.lucasdias.factcatalog.domain.model.Fact

internal interface FactCatalogRepository {
    suspend fun searchFactsBySubjectFromApi(subject: String)
    fun getAllFacts(): LiveData<List<Fact>>
}
