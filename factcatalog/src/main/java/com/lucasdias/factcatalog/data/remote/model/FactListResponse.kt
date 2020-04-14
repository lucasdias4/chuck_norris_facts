package com.lucasdias.factcatalog.data.remote.model

import com.google.gson.annotations.SerializedName

internal data class FactListResponse(
    val total: Int,
    @SerializedName("result") val facts: List<FactResponse>
)
