package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class SearchFactsBySubjectFromApi(
    private val factCatalogRepository: FactCatalogRepository
) {
    suspend operator fun invoke(subject: String): RequestStatus =
        factCatalogRepository.searchFactsBySubjectFromApi(subject = subject)
}
