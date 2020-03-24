package com.lucasdias.log

import android.util.Log

/**
 * Este módulo tem como objetivo ser uma biblioteca. Logo, seus métodos existem independente
 * de serem utilizados pelo client, já que a lib não tem noção do contexto externo.
 **/

object LogApp {

    private var debugMode = false

    fun initialize(debugMode: Boolean) {
        this.debugMode = debugMode
    }

    fun d(title: String, description: String) {
        if (debugMode) Log.d(title, description)
    }

    fun e(title: String, description: String) {
        if (debugMode) Log.e(title, description)
    }

    fun i(title: String, description: String) {
        if (debugMode) Log.i(title, description)
    }

    fun w(title: String, description: String) {
        if (debugMode) Log.w(title, description)
    }

    fun v(title: String, description: String) {
        if (debugMode) Log.v(title, description)
    }
}
