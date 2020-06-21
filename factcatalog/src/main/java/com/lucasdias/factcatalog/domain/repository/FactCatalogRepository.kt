package com.lucasdias.factcatalog.domain.repository

import androidx.lifecycle.LiveData
import com.lucasdias.core_components.base.domain.repository.SuspendableRepository
import com.lucasdias.factcatalog.domain.model.Fact

internal interface FactCatalogRepository : SuspendableRepository {
    fun getAllFacts(): LiveData<List<Fact>>
    fun deleteAllFacts()
}
