package com.ricardolfernandes.catapi.network

interface CatApiRepository {
    suspend fun getCatBreedsList(pageLimit: Int,pageNumber: Int): List< CatBreedDTO>
    suspend fun getCatBreedDetails(id: String): CatBreedsDetailsDTO?
}