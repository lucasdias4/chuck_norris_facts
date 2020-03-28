package com.lucasdias.factcatalog.data.searchtext

import android.content.Context
import com.lucasdias.factcatalog.data.searchtext.local.SearchTextCache
import com.lucasdias.factcatalog.domain.repository.SearchTextRepository

class SearchTextRepositoryImpl(
    private val searchTextCache: SearchTextCache,
    private val context: Context
) : SearchTextRepository {

    override fun setActualSearchText(searchText: String) {
        searchTextCache.setActualSearchText(searchText, context)
    }

    override fun getActualSearchText(): String {
        val actualSearchText = searchTextCache.getActualSearchText(context)
        return actualSearchText
    }
}
