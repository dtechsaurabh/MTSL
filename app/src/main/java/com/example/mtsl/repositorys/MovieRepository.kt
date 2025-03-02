package com.example.mtsl.repositorys

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.agatsa.sanketriskusers.network.api.RetrofitProvider
import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.db.FavoritesDatabase
import com.example.mtsl.db.toDomainMovie
import com.example.mtsl.db.toFavoriteEntity
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(context: Context) {

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    private val favoriteDao = FavoritesDatabase.getInstance(context).favoritesDao()

    fun getPopularMovies(): LiveData<List<Movie>> = moviesLiveData

    suspend fun fetchPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val localMovies = favoriteDao.getFavoriteMoviesList()

        if (localMovies.isNotEmpty()) {
            val movies = localMovies.map { it.toDomainMovie() }
            moviesLiveData.postValue(movies)
            return@withContext movies
        }

        try {
            val response = RetrofitProvider.webService.getPopularMovies(ApiConstants.API_KEY).execute()
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                moviesLiveData.postValue(movies)
                saveMoviesToDatabase(movies)
                return@withContext movies
            } else {
                Log.e("MovieRepository", "API Error: ${response.message()}")
                return@withContext emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movies: ${e.message}")
            return@withContext emptyList()
        }
    }

    fun searchMovies(query: String): LiveData<List<Movie>> {
        val searchLiveData = MutableLiveData<List<Movie>>()

        RetrofitProvider.webService.searchMovies(ApiConstants.API_KEY, query)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        searchLiveData.postValue(response.body()?.results ?: emptyList())
                    } else {
                        Log.e("MovieRepository", "Search API Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e("MovieRepository", "Error searching movies: ${t.message}")
                    searchLiveData.postValue(emptyList())
                }
            })

        return searchLiveData
    }

    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity> = withContext(Dispatchers.IO) {
        favoriteDao.getFavoriteMoviesList()
    }

    suspend fun addFavorite(movie: FavoriteMovieEntity) = withContext(Dispatchers.IO) {
        favoriteDao.addFavorite(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovieEntity) = withContext(Dispatchers.IO) {
        favoriteDao.removeFavorite(movie)
    }

    suspend fun isFavorite(movieId: Int): Boolean = withContext(Dispatchers.IO) {
        favoriteDao.isFavorite(movieId) > 0
    }

    suspend fun saveMoviesToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {
        val movieEntities = movies.mapNotNull { it.toFavoriteEntity() }
        favoriteDao.addMovies(movieEntities)
    }
}
