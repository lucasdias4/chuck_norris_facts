package com.lucasdias.search.data.category.local

import android.content.Context
import androidx.preference.PreferenceManager

internal class CategoryCache(private var context: Context) {

    private companion object {
        const val CATEGORY_KEY = "CATEGORY_KEY"
        const val EMPTY_STRING = ""
        const val DELIMITERS = ","
    }

    fun setCategories(categories: List<String>?) {
        val categoriesAsString = categories?.joinToString()
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(CATEGORY_KEY, categoriesAsString)
        editor.apply()
    }

    fun getCategories(): List<String> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val categoriesAsString = preferences.getString(CATEGORY_KEY, EMPTY_STRING)
        val categories = categoriesAsString?.split(DELIMITERS) ?: emptyList()
        return categories
    }

    fun isCategoryCacheEmpty(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val categoriesAsString = preferences.getString(CATEGORY_KEY, EMPTY_STRING)
        val isNullOrEmpty = categoriesAsString.isNullOrEmpty()
        return isNullOrEmpty
    }
}
