package com.lucasdias.factcatalog.domain.model

internal data class Fact(
    val id: String,
    val value: String,
    val url: String,
    var categories: List<String>?
)
