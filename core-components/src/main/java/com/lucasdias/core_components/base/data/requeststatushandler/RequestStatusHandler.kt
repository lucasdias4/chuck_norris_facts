package com.lucasdias.core_components.base.data.requeststatushandler

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.ClientError
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.GenericError
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.Informational
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.Redirection
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.ServerError
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.Success
import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus.SuccessWithoutData

class RequestStatusHandler {

    companion object {

        fun <Data> execute(code: Int?, data: Data?): RequestStatus {

            return code?.let { nonNullCode ->
                when (nonNullCode) {

                    in MIN_INFORMATIONAL_CODE..MAX_INFORMATIONAL_CODE ->
                        Informational(nonNullCode)

                    in MIN_SUCCESS_CODE..MAX_SUCCESS_CODE -> {
                        val successType = successHandler(code, data)
                        return successType
                    }

                    in MIN_REDIRECTION_CODE..MAX_REDIRECTION_CODE ->
                        Redirection(nonNullCode)

                    in MIN_CLIENT_ERROR_CODE..MAX_CLIENT_ERROR_CODE ->
                        ClientError(nonNullCode)

                    in MIN_SERVER_ERROR_CODE..MAX_SERVER_ERROR_CODE ->
                        ServerError(nonNullCode)

                    else -> GenericError(nonNullCode)
                }
            } ?: run {
                GenericError()
            }
        }

        private fun <Data> successHandler(nonNullCode: Int, data: Data?): RequestStatus {
            return if ((data is ArrayList<*> && data.isEmpty()) || data == null) SuccessWithoutData(nonNullCode)
            else Success(nonNullCode)
        }

        private const val MIN_INFORMATIONAL_CODE = 100
        private const val MIN_SUCCESS_CODE = 200
        private const val MIN_REDIRECTION_CODE = 300
        private const val MIN_CLIENT_ERROR_CODE = 400
        private const val MIN_SERVER_ERROR_CODE = 500
        private const val MAX_INFORMATIONAL_CODE = 199
        private const val MAX_SUCCESS_CODE = 299
        private const val MAX_REDIRECTION_CODE = 399
        private const val MAX_CLIENT_ERROR_CODE = 499
        private const val MAX_SERVER_ERROR_CODE = 599
    }
}
