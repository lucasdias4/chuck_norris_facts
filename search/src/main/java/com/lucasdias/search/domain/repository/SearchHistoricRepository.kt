package com.lucasdias.search.domain.repository

internal interface SearchHistoricRepository {
    fun setSearch(search: String)
    fun getHistoric(): MutableList<String>
}
