package com.lucasdias.factcatalog.domain.usecase

import androidx.lifecycle.LiveData
import com.lucasdias.core_components.base.domain.BaseUseCase
import com.lucasdias.core_components.base.domain.model.None
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class GetAllFactsFromDatabase(
    private val factCatalogRepository: FactCatalogRepository
) : BaseUseCase<None, LiveData<List<Fact>>>() {

    override operator fun invoke(parameter: None): LiveData<List<Fact>> =
            factCatalogRepository.getAllFacts()
}
