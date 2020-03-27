package com.lucasdias.factcatalog.data.fact

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.lucasdias.factcatalog.data.fact.remote.FactCatalogService
import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.log.LogApp

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

        logRequestInfo(result.component1(), subject)
        return result.component1()
    }

    private fun logRequestInfo(
        factListResponse: FactListResponse?,
        subject: String
    ) {
        LogApp.i("FactCatalog", "Request response START ---------->")
        LogApp.i("FactCatalog", "Place: $subject")
        factListResponse?.facts?.forEach { fact ->
            LogApp.i(
                "FactCatalog", " \nFact id: ${fact.id}" +
                        "\nCategories: ${fact.categories}" +
                        "\nUrl: ${fact.url}" +
                        "\nCreated_at: ${fact.created_at}" +
                        "\nUpdate_at: ${fact.update_at}" +
                        "\nIcon_url: ${fact.icon_url}" +
                        "\nValue: ${fact.value}"
            )
        }
        LogApp.i("FactCatalog", "Request response END <----------")
    }
}
