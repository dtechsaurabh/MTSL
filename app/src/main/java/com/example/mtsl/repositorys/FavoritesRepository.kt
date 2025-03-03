package com.example.mtsl.repositorys


import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.db.FavoritesDao
import com.example.mtsl.db.MovieDao
import com.example.mtsl.models.Movie


class FavoriteRepository(private val movieDao: MovieDao) {

    fun getFavoriteMovies(): LiveData<List<Movie>> {
        return movieDao.getFavoriteMovies().asLiveData()
    }
}
