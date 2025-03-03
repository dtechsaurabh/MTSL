package com.example.mtsl.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import com.example.mtsl.db.MovieDatabase
import com.example.mtsl.models.Movie
import com.example.mtsl.repositorys.FavoriteRepository

import kotlinx.coroutines.launch



class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao = MovieDatabase.getDatabase(application).movieDao()
    private val repository: FavoriteRepository = FavoriteRepository(movieDao)

    val favoriteMovies: LiveData<List<Movie>> = repository.getFavoriteMovies()

    // ✅ Function to remove a favorite movie
    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
        }
    }
}
