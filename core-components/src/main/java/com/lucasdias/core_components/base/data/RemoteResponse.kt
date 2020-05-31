package com.lucasdias.core_components.base.data

import java.lang.Exception

@Suppress("unused")
sealed class RemoteResponse<V : Any, E : Exception> {

    abstract fun value(): V?
    abstract fun error(): E?

    fun isSuccess() = this is Success
    fun isError() = this is Error

    data class Success<V : Any, E : Exception>(private val value: V?) : RemoteResponse<V, E>() {
        override fun value(): V? = value
        override fun error(): E? = null

        override fun toString(): String = "{Success: $value}"
    }

    data class Error<V : Any, E : Exception>(val error: E?) : RemoteResponse<V, E>() {
        override fun value(): V? = null
        override fun error(): E? = error

        override fun toString() = "{Error: $error}"
    }

    companion object {
        suspend fun <V : Any, E : Exception> of(suspendFunction: suspend () -> V): RemoteResponse<V, E> = try {
            val value = suspendFunction()
            Success(value)
        } catch (ex: Exception) {
            val error = ex as E
            Error(error)
        }
    }
}
