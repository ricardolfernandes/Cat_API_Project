package com.ricardolfernandes.catapi.modules

import android.content.Context
import com.ricardolfernandes.catapi.database.DBRepoImpl
import com.ricardolfernandes.catapi.network.CatApiRepository
import com.ricardolfernandes.catapi.network.CatApiServices
import com.ricardolfernandes.catapi.network.CatApiServicesImpl
import com.ricardolfernandes.catapi.network.RetrofitInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    private val API_BASE_URL = "https://api.thecatapi.com/v1/"

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
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideCatApiServices(catApiServices: CatApiServices, dbRepo: DBRepoImpl, @ApplicationContext appContext: Context): CatApiRepository = CatApiServicesImpl(catApiServices, dbRepo, appContext)
}