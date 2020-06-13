package com.lucasdias.search.domain.repository

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus

interface CategoryRepository {
    suspend fun searchCategoriesFromApi(): RequestStatus
    fun getCategories(): List<String>?
    fun isCategoryCacheEmpty(): Boolean
}
