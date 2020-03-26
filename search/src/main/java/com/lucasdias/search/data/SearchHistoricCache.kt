package com.lucasdias.search.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

internal object SearchHistoricCache {
    private val searchHistoric = ArrayList<String>()

    private val historic = MutableLiveData<ArrayList<String>>()
    fun getHistoric(): LiveData<ArrayList<String>> = historic

    fun setSearch(search: String) {
        searchHistoric.add(search)
        historic.postValue(searchHistoric)
    }
}
