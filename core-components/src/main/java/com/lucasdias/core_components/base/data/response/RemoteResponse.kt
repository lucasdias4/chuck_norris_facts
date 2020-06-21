package com.lucasdias.core_components.base.data.response

import java.lang.Exception

@Suppress("unused")
sealed class RemoteResponse<V : Any, E : Exception> {

    abstract fun value(): V?
    abstract fun error(): E?

    fun isSuccess() = this is Successful
    fun isError() = this is Failed

    data class Successful<V : Any, E : Exception>(private val value: V?) : RemoteResponse<V, E>() {
        override fun value(): V? = value
        override fun error(): E? = null

        override fun toString(): String = "Success: $value"
    }

    data class Failed<V : Any, E : Exception>(val error: E?) : RemoteResponse<V, E>() {
        override fun value(): V? = null
        override fun error(): E? = error

        override fun toString() = "Error: $error"
    }

    companion object {
        suspend fun <V : Any, E : Exception> of(suspendFunction: suspend () -> V): RemoteResponse<V, E> = try {
            val value = suspendFunction()
            Successful(
                value
            )
        } catch (ex: Exception) {
            val error = ex as E
            Failed(
                error
            )
        }
    }
}
