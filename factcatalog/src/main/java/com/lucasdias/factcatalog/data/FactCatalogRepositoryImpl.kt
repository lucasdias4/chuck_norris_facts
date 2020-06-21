package com.lucasdias.factcatalog.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lucasdias.core_components.base.data.repository.SuspendableRepositoryImpl
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.data.response.RemoteResponse
import com.lucasdias.extensions.itemsTypeAre
import com.lucasdias.factcatalog.data.local.FactCatalogDao
import com.lucasdias.factcatalog.data.local.model.FactData
import com.lucasdias.factcatalog.data.mapper.FactMapper
import com.lucasdias.factcatalog.data.remote.FactCatalogService
import com.lucasdias.factcatalog.data.remote.model.FactListResponse
import com.lucasdias.factcatalog.data.remote.model.FactResponse
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.factcatalog.domain.repository.FactCatalogRepository
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
internal class FactCatalogRepositoryImpl(
    private val factCatalogService: FactCatalogService,
    private val factCatalogDao: FactCatalogDao
) : SuspendableRepositoryImpl(), FactCatalogRepository {

    override fun getAllFacts(): LiveData<List<Fact>> =
        Transformations.map(factCatalogDao.getAllFacts()) {
            FactMapper.mapLocalToDomain(it)
        }

    override fun deleteAllFacts() = factCatalogDao.deleteAllFacts()

    override suspend fun fetch(parameter: String?): RequestStatus {
        val response: RemoteResponse<Response<FactListResponse>, Exception> =
            RemoteResponse.of {
                factCatalogService.searchFactsBySubjectFromApi(
                    subject = parameter
                )
            }

        return responseHandler(
            data = response.value()?.body()?.facts,
            requestCode = response.value()?.code(),
            exception = response.error()
        )
    }

    override fun <Data> onSuccess(data: List<Data>?) {
        var databaseFacts: List<FactData>? = null

        if (data.itemsTypeAre<FactResponse>()) {
            databaseFacts = data?.let { facts ->
                FactMapper.mapRemoteToLocal(facts as List<FactResponse>)
            }
        }

        factCatalogDao.insertFacts(facts = databaseFacts)
    }

    override fun onFail(exception: java.lang.Exception?, resultCode: Int?) {}
}
