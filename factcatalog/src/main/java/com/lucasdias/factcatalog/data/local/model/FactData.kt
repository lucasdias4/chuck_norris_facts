package com.lucasdias.factcatalog.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact")
internal data class FactData(
    @PrimaryKey
    val id: String,
    val value: String,
    val url: String,
    var categoryListAsString: String?
) {
    fun setCategories(categories: ArrayList<String>?) {
        this.categoryListAsString = categories?.joinToString()
    }

    fun getCategories(): List<String>? {
        val delimiters = ","
        return categoryListAsString?.split(delimiters)
    }
}
