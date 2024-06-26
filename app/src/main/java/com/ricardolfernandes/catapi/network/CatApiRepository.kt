package com.ricardolfernandes.catapi.network

interface CatApiRepository {
    suspend fun getCatBreedsList(pageLimit: Int,pageNumber: Int,hasBreeds: Boolean): List<CatBreedsDTO>
    suspend fun getCatBreedDetails(id: String): CatBreedDetailsDTO?
}