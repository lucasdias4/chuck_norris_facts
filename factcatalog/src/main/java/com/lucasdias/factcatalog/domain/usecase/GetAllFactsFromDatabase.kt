package com.lucasdias.factcatalog.domain.usecase

import androidx.lifecycle.LiveData
import com.lucasdias.core_components.base.domain.usecase.BaseUseCase
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class GetAllFactsFromDatabase(
    private val factCatalogRepository: FactCatalogRepository
) : BaseUseCase<Unit?, LiveData<List<Fact>>> {

    override operator fun invoke(parameter: Unit?): LiveData<List<Fact>> =
            factCatalogRepository.getAllFacts()
}
