package com.example.data.di

import com.example.data.repository.MoviesRepository
import com.example.data.repository.MoviesRepositoryImpl
import com.example.data.sync.MovieSyncManager
import com.example.data.sync.SyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    abstract fun bindsMoviesRepository(
        impl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    internal abstract fun bindsSyncStatusMonitor(
        syncStatusMonitor: MovieSyncManager,
    ): SyncManager
}