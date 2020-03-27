package com.lucasdias.factcatalog.domain.usecase

import androidx.lifecycle.LiveData
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class GetAllFactsFromDatabase(
    private val factCatalogRepository: FactCatalogRepository
) {
    operator fun invoke(): LiveData<List<Fact>> = factCatalogRepository.getAllFacts()
}
