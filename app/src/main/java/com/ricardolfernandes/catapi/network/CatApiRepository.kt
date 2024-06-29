package com.ricardolfernandes.catapi.network

import com.ricardolfernandes.catapi.utils.States
import kotlinx.coroutines.flow.Flow

interface CatApiRepository {
    suspend fun getCatBreedsWithDetails( pageLimit: Int, pageNumber: Int): Flow<States<List<CatBreedsDetailsDTO>>>
    suspend fun getCatBreedDetails(id: String): Flow<States<CatBreedsDetailsDTO?>>
}