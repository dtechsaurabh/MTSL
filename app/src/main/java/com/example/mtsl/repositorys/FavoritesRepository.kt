package com.example.mtsl.repositorys


import androidx.lifecycle.LiveData
import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.db.FavoritesDao


class FavoritesRepository(private val favoritesDao: FavoritesDao) {

    val favoriteMovies: LiveData<List<FavoriteMovieEntity>> = favoritesDao.getFavoriteMovies()

    suspend fun addFavorite(movie: FavoriteMovieEntity) {
        favoritesDao.addFavorite(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovieEntity) {
        favoritesDao.removeFavorite(movie)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return favoritesDao.isFavorite(movieId) > 0
    }
}
