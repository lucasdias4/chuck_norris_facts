package com.lucasdias.core_components.base.domain

/**
 * Os use cases desta aplicação devem ter apenas 1 método público e podem ter N métodos privados.
 */
abstract class SuspendableUseCase <in Parameter, out Response> {

    abstract suspend operator fun invoke(parameter: Parameter): Response
}
