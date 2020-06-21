package com.lucasdias.search.data.category

import com.lucasdias.core_components.base.data.repository.BaseRemoteRepositoryImpl
import com.lucasdias.core_components.request.response.RequestResponse
import com.lucasdias.core_components.request.statushandler.RequestStatus
import com.lucasdias.extensions.itemsTypeAre
import com.lucasdias.search.data.category.local.CategoryCache
import com.lucasdias.search.data.category.remote.CategoryService
import com.lucasdias.search.domain.repository.CategoryRepository
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
internal class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val categoryCache: CategoryCache
) : BaseRemoteRepositoryImpl(), CategoryRepository {

    override fun isCategoryCacheEmpty(): Boolean = categoryCache.isCategoryCacheEmpty()
    override fun getCategories(): List<String>? = categoryCache.getCategories()

    override suspend fun fetch(parameter: String?): RequestStatus {
        val response: RequestResponse<Response<List<String>>, Exception> =
            RequestResponse.of {
                categoryService.searchFactsBySubjectFromApi()
            }

        return responseHandler(
            data = response.value()?.body(),
            requestCode = response.value()?.code(),
            exception = response.error()
        )
    }

    override fun <Data> onSuccess(data: List<Data>?) {
        if (data.itemsTypeAre<String>()) {
            categoryCache.setCategories(categories = data as List<String>?)
        }
    }

    override fun onFail(exception: java.lang.Exception?, resultCode: Int?) {}
}
