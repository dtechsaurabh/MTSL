package com.example.mtsl.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mtsl.db.MovieDatabase
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieDetails
import com.example.mtsl.repositorys.MovieRepository
import com.example.mtsl.utils.Myapp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun fetchMovies() {
        repository.getPopularMovies().observeForever {
            _movies.value = it
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                fetchMovies()
            } else {
                repository.searchMovies(query).observeForever { searchResults ->
                    _movies.postValue(searchResults)
                }
            }
        }
    }


    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                repository.fetchMovieDetails(movieId).observeForever { movieDetails ->
                    _movieDetails.postValue(movieDetails!!)
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movie details", e)
            }
        }
    }

    class Factory(private val repository: MovieRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val movieDao = MovieDatabase.getDatabase(Myapp.instance!!).movieDao()

    val favoriteMovies: LiveData<List<Movie>> = movieDao.getFavoriteMovies().asLiveData()

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.isFavorite) {
                movieDao.insertMovie(movie) // Store full movie object
            } else {
                movieDao.deleteMovie(movie)
            }
        }
    }

    fun printFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = movieDao.getFavoriteMovies()
            favorites.collect { movies ->
                Log.d("FavoriteMovies", "Stored Movies: ${movies.joinToString { it.title }}")
            }
        }

    }
}
