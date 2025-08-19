package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.model.models.ContinueMovieEntity
import com.example.model.models.NewMovieEntity
import com.example.model.models.TopMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertTopMovies(movies: TopMovieEntity)

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertNewMovies(movies: NewMovieEntity)

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertContinueMovies(movies: ContinueMovieEntity)

    @Query("SELECT MAX(`order`) FROM continuemovies ")
    fun getMovieCount(): Int?

    @Query("SELECT * FROM topmovies ")
    fun getAllTopMovies(): Flow<List<TopMovieEntity>>

    @Query("SELECT * FROM newmovies ")
    fun getAllNewMovies(): Flow<List<NewMovieEntity>>

    @Query("SELECT * FROM continuemovies ")
    fun getAllContinueMovies(): Flow<List<ContinueMovieEntity>>

    @Query("DELETE FROM continuemovies WHERE `title` = :title")
    fun deleteContinueMovie(title: String)

    @Query("DELETE FROM newmovies")
    suspend fun deleteAllNewMovies()

    @Transaction
    @Query("DELETE FROM topmovies")
    suspend fun deleteAllTopMovies()

    @Transaction
    @Query("DELETE FROM continuemovies")
    suspend fun deleteAllContinueMovies()

    @Query("SELECT COUNT(*) FROM continuemovies")
    suspend fun getContinueMovieCount(): Int
}