package com.ricardolfernandes.catapi.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val API_BASE_URL = "https://api.thecatapi.com/v1/"

    val api: CatApiServices by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RetrofitInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(CatApiServices::class.java)
    }
}