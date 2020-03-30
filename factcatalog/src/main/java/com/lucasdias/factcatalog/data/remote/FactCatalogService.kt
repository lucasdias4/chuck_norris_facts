package com.lucasdias.factcatalog.data.remote

import com.lucasdias.factcatalog.data.remote.response.FactListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface FactCatalogService {

    @GET("/jokes/search")
    fun searchFactsBySubjectFromApi(@Query("query") subject: String?): Deferred<Response<FactListResponse>>
}
