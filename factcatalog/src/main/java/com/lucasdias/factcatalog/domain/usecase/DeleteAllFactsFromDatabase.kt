package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.core_components.base.domain.BaseUseCase
import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class DeleteAllFactsFromDatabase(
    private val factCatalogRepository: FactCatalogRepository
) : BaseUseCase<None, Unit?>() {

    override fun invoke(parameter: None) = factCatalogRepository.deleteAllFacts()
}
