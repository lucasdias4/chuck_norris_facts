package com.lucasdias.factcatalog.domain.typeconverter

import androidx.room.TypeConverter
import kotlin.collections.ArrayList

class TypeConverter {

    companion object {

        private const val COMMA = ","

        @TypeConverter
        fun arrayListToString(list: List<String>?): String? {
            if (list.isNullOrEmpty()) return null
            val string = StringBuilder()
            for (item in list) {
                val isNotTheLastItemInTheArrayList = (item == list.last()).not()
                if (isNotTheLastItemInTheArrayList) {
                    string.append(item).append(COMMA)
                } else {
                    string.append(item)
                }
            }
            return string.toString()
        }

        @TypeConverter
        fun stringToArrayList(string: String?): ArrayList<String>? {
            when {
                string.isNullOrEmpty() -> {
                    return null
                }
                string.contains(COMMA).not() -> {
                    val list = ArrayList<String>()
                    list.add(string)
                    return list
                }
                else -> {
                    return string.split(COMMA.toRegex()).dropLastWhile { it.isEmpty() } as ArrayList<String>
                }
            }
        }
    }
}
