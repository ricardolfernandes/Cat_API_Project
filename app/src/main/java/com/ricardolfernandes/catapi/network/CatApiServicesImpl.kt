package com.ricardolfernandes.catapi.network

import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatApiServicesImpl @Inject constructor(private val catApiServices: CatApiServices) : CatApiRepository {


    override suspend fun getCatBreedsList(
        pageLimit: Int,
        pageNumber: Int
    ): List<CatBreedDTO> {
        return try {
            val response = catApiServices.getCatBreedsList(pageLimit, pageNumber, 1).awaitResponse()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            val e = exception
            emptyList()
        }
    }

    override suspend fun getCatBreedDetails(id: String): CatBreedsDetailsDTO? {
        return try {
            val response = catApiServices.getCatBreedDetails(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (exception: Exception) {
            val e = exception
            null
        }
    }
}