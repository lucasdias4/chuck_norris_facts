package com.lucasdias.factcatalog.domain.repository

import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse

internal interface FactCatalogRepository {
    suspend fun searchFactsBySubjectFromApi(subject: String): FactListResponse?
}
