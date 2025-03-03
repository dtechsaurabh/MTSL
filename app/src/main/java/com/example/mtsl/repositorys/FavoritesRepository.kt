package com.example.mtsl.repositorys


import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.mtsl.db.MovieDao
import com.example.mtsl.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(private val movieDao: MovieDao) {

    fun getFavoriteMovies(): LiveData<List<Movie>> {
        return movieDao.getFavoriteMovies().asLiveData()
    }

    suspend fun removeFavorite(movie: Movie) {
        withContext(Dispatchers.IO) {  // Run on background thread
            movieDao.deleteMovie(movie)
        }
    }
}
