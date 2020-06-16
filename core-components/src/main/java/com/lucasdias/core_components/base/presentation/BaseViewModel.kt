package com.lucasdias.core_components.base.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    fun launch(
        context: CoroutineContext = coroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, block = block)
}
