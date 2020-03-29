package com.lucasdias.search.domain.sealedclass

sealed class RequestStatus

object Error : RequestStatus()
object Success : RequestStatus()
