package com.lucasdias.search.data.category.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.lucasdias.base.typeconverter.TypeConverter

internal class CategoryCache(private var context: Context) {

    private companion object {
        const val CATEGORY_KEY = "CATEGORY_KEY"
        const val EMPTY_STRING = ""
    }

    fun setCategories(categories: List<String>?) {
        val categoriesAsString = TypeConverter.arrayListToString(list = categories)
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(CATEGORY_KEY, categoriesAsString)
        editor.apply()
    }

    fun getCategories(): ArrayList<String> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val categoriesAsString = preferences.getString(CATEGORY_KEY, EMPTY_STRING)
        val categories = TypeConverter.stringToArrayList(string = categoriesAsString) ?: ArrayList()
        return categories
    }

    fun isCategoryCacheEmpty(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val categoriesAsString = preferences.getString(CATEGORY_KEY, EMPTY_STRING)
        val isNullOrEmpty = categoriesAsString.isNullOrEmpty()
        return isNullOrEmpty
    }
}
