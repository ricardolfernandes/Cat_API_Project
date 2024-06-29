package com.ricardolfernandes.catapi.database

import kotlinx.coroutines.flow.Flow

interface DBRepo {
    suspend fun insert(catBreed: CatBreed)

    suspend fun update(catBreed: CatBreed)

    suspend fun delete(catBreed: CatBreed)

    suspend fun getAllCatBreeds(): Flow<List<CatBreed>>

    suspend fun getCatBreedById(id: String): Flow<CatBreed>

}