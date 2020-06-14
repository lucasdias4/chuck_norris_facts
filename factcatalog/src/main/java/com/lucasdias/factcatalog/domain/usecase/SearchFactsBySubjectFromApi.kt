package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.domain.SuspendableUseCase
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class SearchFactsBySubjectFromApi(
    private val factCatalogRepository: FactCatalogRepository
) : SuspendableUseCase<String, RequestStatus>() {

    override suspend operator fun invoke(parameter: String): RequestStatus =
            factCatalogRepository.searchFactsBySubjectFromApi(subject = parameter)
}
