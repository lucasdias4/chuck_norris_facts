package com.lucasdias.search.domain.repository

import com.lucasdias.search.domain.sealedclass.RequestStatus

interface CategoryRepository {
    suspend fun searchCategoriesFromApi(): RequestStatus
    fun getCategories(): List<String>?
    fun isCategoryCacheEmpty(): Boolean
}
