package com.example.goongoonaloAssignment.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MoviesRepository
import com.example.model.MoviesResponse
import com.example.model.ResultResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MoviesViewModel.Factory::class)
class MoviesViewModel @AssistedInject constructor(
    private val repository: MoviesRepository,
    @Assisted("movieId") val movieId: Int
) : ViewModel() {

    private val _moviesList: MutableStateFlow<MoviesResponse> =
        MutableStateFlow(MoviesResponse(arrayListOf(), arrayListOf(), arrayListOf()))
    val moviesList: StateFlow<MoviesResponse> = _moviesList.asStateFlow()

    init {
        triggerInitialFetch()
    }
    private fun triggerInitialFetch() {
        viewModelScope.launch {
            repository.scheduleInitialMovieFetchAndPeriodicSync()
            repository.getMovies().collect {
                _moviesList.value = it
            }
        }
    }

    fun addtoContinueMovies(movie: ResultResponse) {
        viewModelScope.launch {
            repository.addToContinueMovies(movie)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("movieId") movieId: Int
        ): MoviesViewModel
    }
}
