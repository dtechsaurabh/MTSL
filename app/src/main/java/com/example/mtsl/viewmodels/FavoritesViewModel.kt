package com.example.mtsl.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.db.FavoritesDatabase
import com.example.mtsl.repositorys.FavoritesRepository

import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritesRepository
    val favoriteMovies: LiveData<List<FavoriteMovieEntity>>

    init {
        val favoritesDao = FavoritesDatabase.getInstance(application).favoritesDao()
        repository = FavoritesRepository(favoritesDao)
        favoriteMovies = repository.favoriteMovies
    }

    fun addFavorite(movie: FavoriteMovieEntity) = viewModelScope.launch {
        repository.addFavorite(movie)
    }

    fun removeFavorite(movie: FavoriteMovieEntity) = viewModelScope.launch {
        repository.removeFavorite(movie)
    }

    fun isFavorite(movieId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            callback(repository.isFavorite(movieId))
        }
    }

    fun toggleFavorite(movie: FavoriteMovieEntity) = viewModelScope.launch {
        if (repository.isFavorite(movie.id)) {
            repository.removeFavorite(movie)
        } else {
            repository.addFavorite(movie)
        }
    }
}
