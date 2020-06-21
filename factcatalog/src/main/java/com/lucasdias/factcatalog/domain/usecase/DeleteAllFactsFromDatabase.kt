package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.core_components.base.domain.usecase.BaseUseCase
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class DeleteAllFactsFromDatabase(
    private val factCatalogRepository: FactCatalogRepository
) : BaseUseCase<Unit?, Unit?> {

    override fun invoke(parameter: Unit?) = factCatalogRepository.deleteAllFacts()
}
