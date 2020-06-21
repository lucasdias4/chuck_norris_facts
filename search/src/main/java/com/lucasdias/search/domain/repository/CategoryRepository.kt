package com.lucasdias.search.domain.repository

import com.lucasdias.core_components.base.domain.repository.SuspendableRepository

interface CategoryRepository : SuspendableRepository {
    fun getCategories(): List<String>?
    fun isCategoryCacheEmpty(): Boolean
}
