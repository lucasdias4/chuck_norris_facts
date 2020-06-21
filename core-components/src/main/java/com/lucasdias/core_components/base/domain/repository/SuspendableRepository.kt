package com.lucasdias.core_components.base.domain.repository

import com.lucasdias.core_components.base.data.requeststatushandler.RequestStatus

interface SuspendableRepository {
    suspend fun fetch(parameter: String? = null): RequestStatus
}
