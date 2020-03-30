package com.lucasdias.factcatalog.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucasdias.factcatalog.domain.model.Fact

@Dao
internal interface FactCatalogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacts(facts: List<Fact>?)

    @Query("SELECT * FROM fact")
    fun getAllFacts(): LiveData<List<Fact>>

    @Query("DELETE FROM fact")
    fun deleteAllFacts()
}
