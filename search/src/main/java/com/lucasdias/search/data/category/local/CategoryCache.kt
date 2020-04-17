package com.lucasdias.search.data.category.local

import android.content.SharedPreferences

internal class CategoryCache(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val CATEGORY_KEY = "CATEGORY_KEY"
        const val EMPTY_STRING = ""
        const val DELIMITERS = ","
    }

    fun setCategories(categories: List<String>?) {
        val categoriesAsString = categories?.joinToString()
        val editor = sharedPreferences.edit()
        editor.putString(CATEGORY_KEY, categoriesAsString)
        editor.apply()
    }

    fun getCategories(): List<String> {
        val categoriesAsString = sharedPreferences.getString(CATEGORY_KEY, EMPTY_STRING)
        val categories = categoriesAsString?.split(DELIMITERS) ?: emptyList()
        return categories
    }

    fun isCategoryCacheEmpty(): Boolean {
        val categoriesAsString = sharedPreferences.getString(CATEGORY_KEY, EMPTY_STRING)
        val isNullOrEmpty = categoriesAsString.isNullOrEmpty()
        return isNullOrEmpty
    }
}
