package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class SearchFactsBySubjectFromApi(
    private val factCatalogRepository: FactCatalogRepository
) {
    suspend operator fun invoke(subject: String) = factCatalogRepository
        .searchFactsBySubjectFromApi(subject = subject)
}
