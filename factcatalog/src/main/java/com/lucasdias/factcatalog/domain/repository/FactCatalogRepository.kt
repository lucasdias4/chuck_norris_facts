package com.lucasdias.factcatalog.domain.repository

import androidx.lifecycle.LiveData
import com.lucasdias.core_components.base.domain.repository.BaseRemoteRepository
import com.lucasdias.factcatalog.domain.model.Fact

internal interface FactCatalogRepository : BaseRemoteRepository {
    fun getAllFacts(): LiveData<List<Fact>>
    fun deleteAllFacts()
}
