package com.lucasdias.factcatalog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasdias.factcatalog.data.local.model.FactData

@Database(version = 4, entities = [FactData::class])

internal abstract class FactCatalogDatabase : RoomDatabase() {
    abstract fun factDao(): FactCatalogDao
}
