package com.lucasdias.factcatalog.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatusHandler
import com.lucasdias.core_components.base.data.response.RemoteResponse
import com.lucasdias.core_components.log.LogApp
import com.lucasdias.factcatalog.data.local.FactCatalogDao
import com.lucasdias.factcatalog.data.local.model.FactData
import com.lucasdias.factcatalog.data.mapper.FactMapper
import com.lucasdias.factcatalog.data.remote.FactCatalogService
import com.lucasdias.factcatalog.data.remote.model.FactListResponse
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import retrofit2.Response

internal class FactCatalogRepositoryImpl(
    private val factCatalogService: FactCatalogService,
    private val factCatalogDao: FactCatalogDao
) : FactCatalogRepository {

    override fun getAllFacts(): LiveData<List<Fact>> =
        Transformations.map(factCatalogDao.getAllFacts()) {
            FactMapper.mapLocalToDomain(it)
        }

    override fun deleteAllFacts() = factCatalogDao.deleteAllFacts()

    override suspend fun searchFactsBySubjectFromApi(subject: String): RequestStatus {
        val response: RemoteResponse<Response<FactListResponse>, Exception> =
            RemoteResponse.of {
                factCatalogService.searchFactsBySubjectFromApi(
                    subject = subject
                )
            }

        return responseHandler(response, subject)
    }

    private fun responseHandler(
        response: RemoteResponse<Response<FactListResponse>, Exception>,
        subject: String
    ): RequestStatus {
        val responseStatus = RequestStatusHandler.execute(
            code = response.value()?.code(),
            data = response.value()?.body()?.facts
        )

        if (responseStatus is RequestStatus.Success || responseStatus is RequestStatus.SuccessWithoutData) {
            onSuccess(factListResponse = response.value()?.body(), subject = subject)
        } else {
            logRequestException(exception = response.error(), resultCode = response.value()?.code())
        }

        return responseStatus
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
