package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.factcatalog.domain.sealedclass.RequestStatus

internal class SearchFactsBySubjectFromApi(
    private val factCatalogRepository: FactCatalogRepository
) {
    suspend operator fun invoke(subject: String): RequestStatus =
        factCatalogRepository.searchFactsBySubjectFromApi(subject = subject)
}
