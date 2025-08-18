package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.model.models.ContinueMovieEntity
import com.example.model.models.NewMovieEntity
import com.example.model.models.TopMovieEntity


@Database(entities = [TopMovieEntity::class, ContinueMovieEntity::class, NewMovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}