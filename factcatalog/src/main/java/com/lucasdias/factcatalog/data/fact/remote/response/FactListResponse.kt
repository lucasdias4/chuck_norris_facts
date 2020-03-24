package com.lucasdias.factcatalog.data.fact.remote.response

import com.google.gson.annotations.SerializedName

internal data class FactListResponse(
    var total: Int,
    @SerializedName("result") var facts: List<FactResponse>
)
