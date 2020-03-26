package com.lucasdias.search.domain.repository

import androidx.lifecycle.LiveData

interface SearchHistoricRepository {
    fun setSearch(search: String)
    fun getHistoric(): LiveData<ArrayList<String>>
}
