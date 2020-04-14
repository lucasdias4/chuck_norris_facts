package com.lucasdias.factcatalog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasdias.factcatalog.domain.model.Fact

@Database(version = 3, entities = [Fact::class])

internal abstract class FactCatalogDatabase : RoomDatabase() {
    abstract fun factDao(): FactCatalogDao
}
