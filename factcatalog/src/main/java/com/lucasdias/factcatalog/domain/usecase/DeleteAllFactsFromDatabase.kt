package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class DeleteAllFactsFromDatabase(
    private val factCatalogRepository: FactCatalogRepository
) {
    operator fun invoke() = factCatalogRepository.deleteAllFacts()
}
