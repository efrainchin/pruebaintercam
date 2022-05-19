package com.efrain.intercamprueba.data.dao

import androidx.room.*
import com.efrain.intercamprueba.entities.EntityBeers

@Dao
interface BeersDao {

    @Query("SELECT * FROM beers")
    fun getAllBeers(): List<EntityBeers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserBeer(vararg users: EntityBeers)

    @Delete
    suspend fun delete(beer: EntityBeers)
}