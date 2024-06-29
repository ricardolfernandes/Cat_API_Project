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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "your_database_name"
        ).build()
    }

    @Provides
    fun provideCatBreedDao(database: AppDatabase): CatBreedDao {
        return database.catBreedDao()
    }


}