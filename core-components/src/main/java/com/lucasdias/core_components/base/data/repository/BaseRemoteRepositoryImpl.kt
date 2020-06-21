package com.lucasdias.core_components.base.data.repository

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatusHandler
import com.lucasdias.core_components.log.LogApp

abstract class BaseRemoteRepositoryImpl {

    abstract fun <Data : Any?> onSuccess(data: List<Data>?)
    abstract fun onFail(exception: java.lang.Exception?, resultCode: Int?)

    fun <Data : Any?> responseHandler(
        data: List<Data>?,
        requestCode: Int?,
        exception: java.lang.Exception?
    ): RequestStatus {
        val responseStatus = RequestStatusHandler.execute(requestCode, data)

        if (responseStatus is RequestStatus.Success || responseStatus is RequestStatus.SuccessWithoutData) {
            onSuccess(data)
            logSuccessfulRequest(data)
        } else {
            onFail(exception = exception, resultCode = requestCode)
            logFailedRequest(exception = exception, resultCode = requestCode)
        }

        return responseStatus
    }

    private fun <Data : Any?> logSuccessfulRequest(
        data: List<Data>?
    ) {
        LogApp.i(javaClass.simpleName, "Request response START ---------->")

        when (data) {
            is List<*> -> LogApp.i(javaClass.simpleName, " \n Data: $data")
            else -> LogApp.i(javaClass.simpleName, " \n Response data: $data")
        }
        LogApp.i(javaClass.simpleName, "Request response END <----------")
    }

    private fun logFailedRequest(exception: java.lang.Exception?, resultCode: Int?) {
        LogApp.i(javaClass.simpleName, "Failed request START ---------->")
        LogApp.i(javaClass.simpleName, "Exception or ErrorCode: ${exception ?: resultCode}")
        LogApp.i(javaClass.simpleName, "Failed request END <----------")
    }
}
