package com.lucasdias.factcatalog.data.fact

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.lucasdias.factcatalog.data.fact.remote.FactCatalogService
import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository

internal class FactCatalogRepositoryImpl(
    private val factCatalogService: FactCatalogService
) : FactCatalogRepository {
    override suspend fun searchFactsBySubjectFromApi(subject: String): FactListResponse? {

        val result: SuspendableResult<FactListResponse, Exception> =
            SuspendableResult.of {
                factCatalogService.searchFactsBySubjectFromApi(
                    subject = subject
                ).await()
            }
        return result.component1()
    }
}
