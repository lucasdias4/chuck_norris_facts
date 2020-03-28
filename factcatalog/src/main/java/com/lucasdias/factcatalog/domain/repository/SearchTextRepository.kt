package com.lucasdias.factcatalog.domain.repository

interface SearchTextRepository {
    fun setActualSearchText(searchText: String)
    fun getActualSearchText(): String
}
