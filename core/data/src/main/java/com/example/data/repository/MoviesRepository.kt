package com.example.data.repository

import com.example.data.MovieDao
import com.example.data.sync.SyncManager
import com.example.data.sync.Syncable
import com.example.data.sync.Synchronizer
import com.example.model.MoviesResponse
import com.example.model.ResultResponse
import com.example.model.models.asExternalModel
import com.example.model.toContinueMovieEntity
import com.example.model.toNewMovieEntity
import com.example.model.toTopMovieEntity
import com.example.network.MoviesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MoviesRepository : Syncable {

    suspend fun getMovies(): Flow<MoviesResponse>

    suspend fun scheduleInitialMovieFetchAndPeriodicSync()

    suspend fun movieSyncing(): Flow<Boolean>

    suspend fun shouldFetchInitialMovies(): Boolean

    suspend fun addToContinueMovies(movie: ResultResponse)

    suspend fun deleteAllMovies()

}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val syncManager: SyncManager,
    private val movieDao: MovieDao
) : MoviesRepository {
    override suspend fun getMovies(): Flow<MoviesResponse> = combine(
        movieDao.getAllTopMovies(),
        movieDao.getAllNewMovies(),
        movieDao.getAllContinueMovies(),
    ) { topMovies, newMovies, continueMovies ->
        MoviesResponse(
            ArrayList(topMovies.map { it.asExternalModel() }.sortedBy { it.order }),
            ArrayList(newMovies.map { it.asExternalModel() }.sortedBy { it.order }),
            ArrayList(continueMovies.map { it.asExternalModel() }.sortedByDescending { it.order })
        )
    }

    override suspend fun scheduleInitialMovieFetchAndPeriodicSync() {
        syncManager.requestSync()
        syncManager.requestPeriodicSync()
    }

    override suspend fun movieSyncing(): Flow<Boolean> =
        syncManager.isSyncing

    override suspend fun shouldFetchInitialMovies(): Boolean {
        return movieDao.getContinueMovieCount() == 0
    }

    override suspend fun addToContinueMovies(movie: ResultResponse): Unit =
        withContext(Dispatchers.IO) {
            movieDao.deleteContinueMovie(movie.title ?: "")
            movieDao.insertContinueMovies(
                movie.toContinueMovieEntity((movieDao.getMaxOrderInContinueMovies() ?: 0) + 1)
            )
            getMovies()
        }

    override suspend fun deleteAllMovies() = withContext(Dispatchers.IO) {
        movieDao.deleteAllNewMovies()
        movieDao.deleteAllTopMovies()
        movieDao.deleteAllContinueMovies()
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        deleteAllMovies()
        val movieService = moviesApiService.getMovies()
        val topMovieEntity = movieService.topMovies
        val newMovieEntity = movieService.newMovies
        val continueMovieEntity = movieService.continueMovies
        topMovieEntity.forEach {
            movieDao.insertTopMovies(it.toTopMovieEntity())
        }
        newMovieEntity.forEach {
            movieDao.insertNewMovies(it.toNewMovieEntity())
        }
        continueMovieEntity.forEach {
            movieDao.insertContinueMovies(it.toContinueMovieEntity(it.order))
        }
        return true
    }
}