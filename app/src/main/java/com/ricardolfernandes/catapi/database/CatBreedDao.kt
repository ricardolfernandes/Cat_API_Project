package com.ricardolfernandes.catapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatBreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catBreed: CatBreed)

    @Update
    suspend fun update(catBreed: CatBreed)

    @Delete
    suspend fun delete(catBreed: CatBreed)

    @Query("SELECT * FROM catbreed")
    fun getAllCatBreeds(): Flow<List<CatBreed>>

    @Query("SELECT * FROM catbreed WHERE id = :id")
    fun getCatBreedById(id: String): Flow<CatBreed>

}