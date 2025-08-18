package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.MovieDao
import com.example.data.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesUsersDao(
        database: MovieDatabase,
    ): MovieDao = database.movieDao()


    @Provides
    @Singleton
    fun providesMovieDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "movie-database",
    ).build()
}

