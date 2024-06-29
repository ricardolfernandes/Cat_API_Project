package com.ricardolfernandes.catapi.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepoImpl @Inject constructor(private val catBreedDao: CatBreedDao) {

    suspend fun insert(catBreed: CatBreed) {
        catBreedDao.insert(catBreed)
    }

    suspend fun update(catBreed: CatBreed) {
        catBreedDao.update(catBreed)
    }

    suspend fun delete(catBreed: CatBreed) {
        catBreedDao.delete(catBreed)
    }

    fun getAllCatBreeds(): Flow<List<CatBreed>> {
        return catBreedDao.getAllCatBreeds()
    }

    fun getAllFavouritesCatBreeds(): Flow<List<CatBreed>> {
        return catBreedDao.getAllFavouritesCatBreeds()
    }

    fun getCatBreedById(id: String): Flow<CatBreed> {
        return catBreedDao.getCatBreedById(id)
    }
}