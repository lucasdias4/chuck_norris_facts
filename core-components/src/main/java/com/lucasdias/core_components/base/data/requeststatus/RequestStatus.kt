package com.lucasdias.core_components.base.data.requeststatus

import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.BAD_REQUEST
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.CONFLICT
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.CREATED
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.FORBIDDEN
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.INTERNAL_SERVER_ERROR
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.NOT_FOUND
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.NOT_MODIFIED
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.NO_CONTENT
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.OK
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.UNAUTHORIZED
import com.lucasdias.core_components.base.data.requeststatus.RequestStatus.Type.UNCATEGORIZED

sealed class RequestStatus {

    abstract val code: Int
    abstract fun getType(): Type

    data class Informational(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = UNCATEGORIZED
    }

    data class Success(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = when (code) {
            OK.code -> OK
            CREATED.code -> CREATED
            NO_CONTENT.code -> NO_CONTENT
            else -> UNCATEGORIZED
        }
    }

    data class SuccessWithoutData(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = when (code) {
            OK.code -> OK
            CREATED.code -> CREATED
            NO_CONTENT.code -> NO_CONTENT
            else -> UNCATEGORIZED
        }
    }

    data class Redirection(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = if (code == NOT_MODIFIED.code) NOT_MODIFIED
        else UNCATEGORIZED
    }

    data class ClientError(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = when (code) {
            BAD_REQUEST.code -> BAD_REQUEST
            FORBIDDEN.code -> FORBIDDEN
            CONFLICT.code -> CONFLICT
            UNAUTHORIZED.code -> UNAUTHORIZED
            NOT_FOUND.code -> NOT_FOUND
            else -> UNCATEGORIZED
        }
    }

    data class ServerError(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = if (code == INTERNAL_SERVER_ERROR.code) INTERNAL_SERVER_ERROR
        else UNCATEGORIZED
    }

    data class GenericError(override val code: Int = UNCATEGORIZED.code) : RequestStatus() {
        override fun getType() = UNCATEGORIZED
    }

    enum class Type(val code: Int) {
        UNCATEGORIZED(code = -1),
        OK(code = 200),
        CREATED(code = 201),
        NO_CONTENT(code = 204),
        NOT_MODIFIED(code = 304),
        BAD_REQUEST(code = 400),
        UNAUTHORIZED(code = 401),
        FORBIDDEN(code = 403),
        NOT_FOUND(code = 404),
        CONFLICT(code = 409),
        INTERNAL_SERVER_ERROR(code = 500)
    }
}
