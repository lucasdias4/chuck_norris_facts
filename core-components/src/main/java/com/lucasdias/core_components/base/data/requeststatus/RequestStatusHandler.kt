package com.lucasdias.core_components.base.data.requeststatus

class RequestStatusHandler {

    companion object {

        fun execute(code: Int) = when (code) {
            in MIN_INFORMATIONAL_CODE..MAX_INFORMATIONAL_CODE ->
                RequestStatus.Informational(code)

            in MIN_SUCCESS_CODE..MAX_SUCCESS_CODE ->
                RequestStatus.Success(code)

            in MIN_REDIRECTION_CODE..MAX_REDIRECTION_CODE ->
                RequestStatus.Redirection(code)

            in MIN_CLIENT_ERROR_CODE..MAX_CLIENT_ERROR_CODE ->
                RequestStatus.ClientError(code)

            in MIN_SERVER_ERROR_CODE..MAX_SERVER_ERROR_CODE ->
                RequestStatus.ServerError(code)

            else -> null
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
