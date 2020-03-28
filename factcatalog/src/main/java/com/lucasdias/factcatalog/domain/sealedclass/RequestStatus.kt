package com.lucasdias.factcatalog.domain.sealedclass

sealed class RequestStatus

object Error : RequestStatus()
object SuccessWithoutResult : RequestStatus()
object Success : RequestStatus()
