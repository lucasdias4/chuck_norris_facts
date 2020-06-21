package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.core_components.base.domain.usecase.BaseSuspendableUseCase
import com.lucasdias.core_components.request.statushandler.RequestStatus
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class SearchFactsBySubjectFromApi(
    private val factCatalogRepository: FactCatalogRepository
) : BaseSuspendableUseCase<String, RequestStatus> {

    override suspend operator fun invoke(parameter: String?): RequestStatus =
            factCatalogRepository.fetch(parameter)
}
