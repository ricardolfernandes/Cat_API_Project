package com.ricardolfernandes.catapi.modules

import android.content.Context
import androidx.room.Room
import com.ricardolfernandes.catapi.database.AppDatabase
import com.ricardolfernandes.catapi.database.CatBreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    private const val DATABASE_NAME = "cat_breeds_database";
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCatBreedDao(database: AppDatabase): CatBreedDao {
        return database.catBreedDao()
    }


}