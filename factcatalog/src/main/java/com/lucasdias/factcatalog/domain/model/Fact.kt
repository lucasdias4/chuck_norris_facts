package com.lucasdias.factcatalog.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lucasdias.base.typeconverter.TypeConverter

@Entity(tableName = "fact")
internal data class Fact(
    @PrimaryKey
    val id: String,
    val value: String,
    val url: String,
    var categoryListAsString: String?
) {
    fun setCategories(categories: ArrayList<String>?) {
        this.categoryListAsString = TypeConverter.arrayListToString(categories)
    }

    fun getCategories(): ArrayList<String>? {
        return TypeConverter.stringToArrayList(categoryListAsString)
    }
}
