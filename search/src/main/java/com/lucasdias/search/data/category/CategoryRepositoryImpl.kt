package com.lucasdias.search.data.category

import com.lucasdias.core_components.base.data.RemoteResponse
import com.lucasdias.core_components.log.LogApp
import com.lucasdias.search.data.category.local.CategoryCache
import com.lucasdias.search.data.category.remote.CategoryService
import com.lucasdias.search.domain.repository.CategoryRepository
import com.lucasdias.search.domain.sealedclass.Error
import com.lucasdias.search.domain.sealedclass.RequestStatus
import com.lucasdias.search.domain.sealedclass.Success
import retrofit2.Response

internal class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val categoryCache: CategoryCache
) : CategoryRepository {

    private companion object {
        private const val MIN_RESPONSE_CODE = 200
        private const val MAX_RESPONSE_CODE = 299
    }

    override fun isCategoryCacheEmpty(): Boolean = categoryCache.isCategoryCacheEmpty()
    override fun getCategories(): List<String>? = categoryCache.getCategories()

    override suspend fun searchCategoriesFromApi(): RequestStatus {
        val result: RemoteResponse<Response<List<String>>, Exception> =
            RemoteResponse.of {
                categoryService.searchFactsBySubjectFromApi()
            }

        val resultCode = result.value()?.code()
        val resultException = result.error()
        val resultBody = result.value()?.body()
        val status = resultStatusHandler(
            resultCode = resultCode, resultException = resultException
        )

        if (status == Success) {
            onSuccess(
                categories = resultBody
            )
        } else if (status == Error) {
            logRequestException(exception = resultException, resultCode = resultCode)
        }

        return status
    }

    private fun onSuccess(
        categories: List<String>?
    ) {
        categoryCache.setCategories(categories = categories)
        logRequestInfo(categories = categories)
    }

    private fun resultStatusHandler(
        resultCode: Int?,
        resultException: java.lang.Exception?
    ): RequestStatus {
        if (resultCode in MIN_RESPONSE_CODE..MAX_RESPONSE_CODE) {
            val isAnException = resultException != null

            if (isAnException) return Error
            return Success
        } else {
            return Error
        }
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
