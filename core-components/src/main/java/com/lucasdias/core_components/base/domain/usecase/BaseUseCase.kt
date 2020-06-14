package com.lucasdias.core_components.base.domain.usecase

/**
 * Os use cases desta aplicação devem ter apenas 1 método público e podem ter N métodos privados.
 */
interface BaseUseCase <in Parameter, out Response> {

    operator fun invoke(parameter: Parameter? = null): Response
}
