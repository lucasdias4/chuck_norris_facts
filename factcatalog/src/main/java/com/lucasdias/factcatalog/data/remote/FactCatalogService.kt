package com.lucasdias.factcatalog.data.remote

import com.lucasdias.factcatalog.data.remote.model.FactListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface FactCatalogService {

    @GET("/jokes/search")
    suspend fun searchFactsBySubjectFromApi(@Query("query") subject: String?): Response<FactListResponse>
}
