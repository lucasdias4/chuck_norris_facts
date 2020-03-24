package com.lucasdias.factcatalog.data.fact.remote

import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

internal interface FactCatalogService {

    @GET("/jokes/search")
    fun searchFactsBySubjectFromApi(@Query("query") subject: String?): Deferred<FactListResponse>
}
