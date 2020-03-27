package com.lucasdias.factcatalog.data.fact

import androidx.lifecycle.LiveData
import com.github.kittinunf.result.coroutines.SuspendableResult
import com.lucasdias.factcatalog.data.fact.local.FactCatalogDao
import com.lucasdias.factcatalog.data.fact.mapper.FactMapper
import com.lucasdias.factcatalog.data.fact.remote.FactCatalogService
import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.log.LogApp

internal class FactCatalogRepositoryImpl(
    private val factCatalogService: FactCatalogService,
    private val factCatalogDao: FactCatalogDao
) : FactCatalogRepository {

    override fun getAllFacts(): LiveData<List<Fact>> = factCatalogDao.getAllFacts()

    override suspend fun searchFactsBySubjectFromApi(subject: String) {
        val result: SuspendableResult<FactListResponse, Exception> =
            SuspendableResult.of {
                factCatalogService.searchFactsBySubjectFromApi(
                    subject = subject
                ).await()
            }

        onSuccess(result.component1(), subject)
    }

    private fun onSuccess(
        factListResponse: FactListResponse?,
        subject: String
    ) {
        val domainFacts = factListResponse?.let { facts ->
            FactMapper.map(facts)
        }

        logRequestInfo(
            facts = domainFacts,
            subject = subject
        )

        factCatalogDao.insertFacts(facts = domainFacts)
    }

    private fun logRequestInfo(
        facts: ArrayList<Fact>?,
        subject: String
    ) {
        LogApp.i("FactCatalog", "Request response START ---------->")
        LogApp.i("FactCatalog", "Place: $subject")
        facts?.forEach { fact ->
            LogApp.i(
                "FactCatalog", " \nFact id: ${fact.id}" +
                        "\nCategories: ${fact.categorieListAsString}" +
                        "\nUrl: ${fact.url}" +
                        "\nValue: ${fact.value}"
            )
        }
        LogApp.i("FactCatalog", "Request response END <----------")
    }
}
