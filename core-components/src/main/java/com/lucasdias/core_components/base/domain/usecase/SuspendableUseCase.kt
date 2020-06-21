package com.lucasdias.core_components.base.domain.usecase

/**
 * Os use cases desta aplicação devem ter apenas 1 método público e podem ter N métodos privados.
 */
interface SuspendableUseCase <in Parameter, out Response> {

    suspend operator fun invoke(parameter: Parameter? = null): Response
}
