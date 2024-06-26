package com.ricardolfernandes.catapi.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApiServices {
    @GET("images/search")
    suspend fun getCatBreedsList(@Query("limit") pageLimit: Int, @Query("page") pageNumber: Int, @Query("has_breeds") hasBreeds: Boolean): Call<List<CatBreedsDTO>>

    @GET("images/{id}")
    suspend fun getCatBreedDetails(@Path("id") id: String): Call<CatBreedDetailsDTO>
}