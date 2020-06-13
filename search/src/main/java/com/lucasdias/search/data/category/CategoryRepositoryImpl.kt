package com.lucasdias.search.data.category

import com.lucasdias.core_components.base.data.RemoteResponse
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus
import com.lucasdias.core_components.base.data.requeststatus.RequestStatusHandler
import com.lucasdias.core_components.log.LogApp
import com.lucasdias.search.data.category.local.CategoryCache
import com.lucasdias.search.data.category.remote.CategoryService
import com.lucasdias.search.domain.repository.CategoryRepository
import retrofit2.Response

internal class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val categoryCache: CategoryCache
) : CategoryRepository {

    override fun isCategoryCacheEmpty(): Boolean = categoryCache.isCategoryCacheEmpty()
    override fun getCategories(): List<String>? = categoryCache.getCategories()

    override suspend fun searchCategoriesFromApi(): RequestStatus {
        val response: RemoteResponse<Response<List<String>>, Exception> =
            RemoteResponse.of {
                categoryService.searchFactsBySubjectFromApi()
            }

        return responseHandler(response)
    }

    private fun responseHandler(response: RemoteResponse<Response<List<String>>, Exception>): RequestStatus {
        val responseStatus = response.value()?.code()?.let { code ->
            return@let RequestStatusHandler.execute(code)
        } ?: run {
            return@run RequestStatus.GenericError()
        }

        if (responseStatus is RequestStatus.Success) {
            onSuccess(categories = response.value()?.body())
        } else {
            logRequestException(exception = response.error(), resultCode = response.value()?.code())
        }

        return responseStatus
    }

    private fun onSuccess(
        categories: List<String>?
    ) {
        categoryCache.setCategories(categories = categories)
        logRequestInfo(categories = categories)
    }

    private fun logRequestInfo(
        categories: List<String>?
    ) {
        LogApp.i("Search", "Request response START ---------->")
        categories?.forEach { category ->
            LogApp.i(
                "Search",
                "\nCategories: $category"
            )
        }
        LogApp.i("Search", "Request response END <----------")
    }

    private fun logRequestException(exception: java.lang.Exception?, resultCode: Int?) {
        LogApp.i("Search", "Request exception START ---------->")
        LogApp.i("Search", "Exception or ErrorCode: ${exception ?: resultCode}")
        LogApp.i("Search", "Request exception END <----------")
    }
}
