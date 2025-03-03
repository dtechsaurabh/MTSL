package com.example.mtsl.viewmodels

<<<<<<< HEAD
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtsl.models.Movie
import com.example.mtsl.repositorys.MovieRepository
import kotlinx.coroutines.launch
import com.example.mtsl.db.toFavoriteEntity
=======
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieDetails
import com.example.mtsl.repositorys.MovieRepository
import kotlinx.coroutines.launch
>>>>>>> 31c47be (Initial commit)

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

<<<<<<< HEAD
    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            val movies = repository.fetchPopularMovies()
            _movies.postValue(movies)
        }
    }

    fun fetchMovies2() {
        viewModelScope.launch {
            val favoriteMovies = repository.getFavoriteMovies()
            val favoriteMovieIds = favoriteMovies.map { it.id }.toSet()

            if (favoriteMovies.isEmpty()) {
                val movies = repository.fetchPopularMovies()
                movies.forEach { movie ->
                    movie.isFavorite = favoriteMovieIds.contains(movie.id)
                }
                repository.saveMoviesToDatabase(movies)
                _movies.postValue(movies)
            } else {
                val moviesFromDb = favoriteMovies.map { favoriteMovie ->
                    Movie(
                        id = favoriteMovie.id,
                        title = favoriteMovie.title,
                        overview = "",
                        releaseDate = favoriteMovie.releaseDate,
                        posterPath = favoriteMovie.posterPath,
                        voteAverage = 0.0,
                        isFavorite = true
                    )
                }
                _movies.postValue(moviesFromDb)
            }
        }
    }

    fun searchMovies(query: String) {
        if (query.isEmpty()) {
            fetchMovies()
        } else {
            repository.searchMovies(query).observeForever { searchResults ->
                _movies.value = searchResults
=======
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
>>>>>>> 31c47be (Initial commit)
            }
        }
    }

<<<<<<< HEAD
    fun updateFavoriteStatus(movie: Movie) {
        _movies.value = _movies.value?.map {
            if (it.id == movie.id) it.copy(isFavorite = !it.isFavorite)
            else it
        }
    }

    fun toggleFavorite(movie: Movie) = viewModelScope.launch {
        val isFav = repository.isFavorite(movie.id)
        if (isFav) {
            movie.toFavoriteEntity()?.let { repository.removeFavorite(it) }
        } else {
            movie.toFavoriteEntity()?.let { repository.addFavorite(it) }
        }
        updateFavoriteStatus(movie)
    }
=======


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
    class Factory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


>>>>>>> 31c47be (Initial commit)
}
