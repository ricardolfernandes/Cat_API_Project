package com.ricardolfernandes.catapi.modules

import com.ricardolfernandes.catapi.network.CatApiRepository
import com.ricardolfernandes.catapi.network.CatApiServices
import com.ricardolfernandes.catapi.network.CatApiServicesImpl
import com.ricardolfernandes.catapi.network.RetrofitInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    val API_BASE_URL = "https://api.thecatapi.com/v1/"

    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RetrofitInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun catApiServices(retrofit: Retrofit): CatApiServices = retrofit.create(CatApiServices::class.java)

    @Provides
    fun provideCatApiServices(catApiServices: CatApiServices): CatApiRepository = CatApiServicesImpl(catApiServices)
}