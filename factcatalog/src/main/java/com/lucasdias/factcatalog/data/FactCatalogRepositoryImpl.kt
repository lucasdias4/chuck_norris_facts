package com.lucasdias.factcatalog.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.kittinunf.result.coroutines.SuspendableResult
import com.lucasdias.core_components.log.LogApp
import com.lucasdias.factcatalog.data.local.FactCatalogDao
import com.lucasdias.factcatalog.data.local.model.FactData
import com.lucasdias.factcatalog.data.mapper.FactMapper
import com.lucasdias.factcatalog.data.remote.FactCatalogService
import com.lucasdias.factcatalog.data.remote.model.FactListResponse
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import com.lucasdias.factcatalog.domain.sealedclass.Error
import com.lucasdias.factcatalog.domain.sealedclass.RequestStatus
import com.lucasdias.factcatalog.domain.sealedclass.Success
import com.lucasdias.factcatalog.domain.sealedclass.SuccessWithoutResult
import retrofit2.Response

internal class FactCatalogRepositoryImpl(
    private val factCatalogService: FactCatalogService,
    private val factCatalogDao: FactCatalogDao
) : FactCatalogRepository {

    private companion object {
        const val MIN_RESPONSE_CODE = 200
        const val MAX_RESPONSE_CODE = 299
    }

    override fun getAllFacts(): LiveData<List<Fact>> =
        Transformations.map(factCatalogDao.getAllFacts()) {
            FactMapper.mapLocalToDomain(it)
        }

    override fun deleteAllFacts() = factCatalogDao.deleteAllFacts()

    override suspend fun searchFactsBySubjectFromApi(subject: String): RequestStatus {
        val result: SuspendableResult<Response<FactListResponse>, Exception> =
            SuspendableResult.of {
                factCatalogService.searchFactsBySubjectFromApi(
                    subject = subject
                )
            }

        val resultCode = result.component1()?.code()
        val resultBody = result.component1()?.body()
        val status = resultStatusHandler(
            resultCode = resultCode,
            resultBody = resultBody
        )

        if (status == Success) {
            onSuccess(
                factListResponse = resultBody,
                subject = subject
            )
        } else if (status == Error) {
            val exception = result.component2()
            logRequestException(exception = exception, resultCode = resultCode)
        }

        return status
    }

    private fun onSuccess(
        factListResponse: FactListResponse?,
        subject: String
    ) {
        val dataFacts = factListResponse?.let { facts ->
            FactMapper.mapRemoteToLocal(factListResponse = facts)
        }

        logRequestInfo(facts = dataFacts, subject = subject)
        factCatalogDao.insertFacts(facts = dataFacts)
    }

    private fun resultStatusHandler(
        resultCode: Int?,
        resultBody: FactListResponse?
    ): RequestStatus {
        if (resultCode in MIN_RESPONSE_CODE..MAX_RESPONSE_CODE) {
            val searchWithoutResult = resultBody?.facts.isNullOrEmpty()
            if (searchWithoutResult) return SuccessWithoutResult
            return Success
        } else {
            return Error
        }
    }

    private fun logRequestInfo(
        facts: List<FactData>?,
        subject: String
    ) {
        LogApp.i("FactCatalog", "Request response START ---------->")
        LogApp.i("FactCatalog", "Place: $subject")
        facts?.forEach { fact ->
            LogApp.i(
                "FactCatalog", " \nFact id: ${fact.id}" +
                        "\nCategories: ${fact.categoryListAsString}" +
                        "\nUrl: ${fact.url}" +
                        "\nValue: ${fact.value}"
            )
        }
        LogApp.i("FactCatalog", "Request response END <----------")
    }

    private fun logRequestException(exception: java.lang.Exception?, resultCode: Int?) {
        LogApp.i("FactCatalog", "Request exception START ---------->")
        LogApp.i("FactCatalog", "Exception or ErrorCode: ${exception ?: resultCode}")
        LogApp.i("FactCatalog", "Request exception END <----------")
    }
}
