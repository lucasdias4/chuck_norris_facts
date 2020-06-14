package com.lucasdias.extensions

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>?.itemsTypeAre(): Boolean = this?.all { it is T } ?: false
