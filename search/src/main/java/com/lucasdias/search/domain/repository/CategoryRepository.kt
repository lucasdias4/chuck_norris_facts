package com.lucasdias.search.domain.repository

import com.lucasdias.core_components.base.domain.repository.BaseRemoteRepository

interface CategoryRepository : BaseRemoteRepository {
    fun getCategories(): List<String>?
    fun isCategoryCacheEmpty(): Boolean
}
