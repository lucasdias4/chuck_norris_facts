package com.lucasdias.factcatalog.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lucasdias.factcatalog.domain.typeconverter.TypeConverter

@Entity(tableName = "fact")
internal data class Fact(
    @PrimaryKey
    var id: String,
    var value: String,
    var url: String,
    var categorieListAsString: String?
) {
    fun setCategories(categories: ArrayList<String>?) {
        this.categorieListAsString = TypeConverter.arrayListToString(categories)
    }

    fun getCategories(): ArrayList<String>? {
        return TypeConverter.stringToArrayList(categorieListAsString)
    }
}
