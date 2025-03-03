package com.example.mtsl.repositorys

<<<<<<< HEAD
import android.content.Context
=======
>>>>>>> 31c47be (Initial commit)
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.agatsa.sanketriskusers.network.api.RetrofitProvider
import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
<<<<<<< HEAD
import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.db.FavoritesDatabase
import com.example.mtsl.db.toDomainMovie
import com.example.mtsl.db.toFavoriteEntity
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
=======
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieDetails
import com.example.mtsl.models.MovieResponse


>>>>>>> 31c47be (Initial commit)
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

<<<<<<< HEAD
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
=======
class MovieRepository {
    fun getPopularMovies(): LiveData<List<Movie>> {
        val moviesLiveData = MutableLiveData<List<Movie>>()

        RetrofitProvider.webService.getPopularMovies(ApiConstants.API_KEY).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    moviesLiveData.postValue(response.body()?.results ?: emptyList())
                } else {
                    Log.e("MovieRepository", "API Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MovieRepository", "Error fetching movies: ${t.message}")
                moviesLiveData.postValue(emptyList()) // Ensure LiveData is updated
            }
        })

        return moviesLiveData
>>>>>>> 31c47be (Initial commit)
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

<<<<<<< HEAD
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
=======

    fun fetchMovieDetails(movieId: Int): LiveData<MovieDetails?> {
        val movieDetailsLiveData = MutableLiveData<MovieDetails?>()

        Log.d("MovieRepository", "Fetching details for movie ID: $movieId")

        RetrofitProvider.webService.getMovieDetails(movieId, ApiConstants.API_KEY)
            .enqueue(object : Callback<MovieDetails> {
                override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                    if (response.isSuccessful) {
                        val movieDetails = response.body()
                        Log.d("MovieRepository", "Fetched movie details: $movieDetails")
                        movieDetailsLiveData.postValue(movieDetails)
                    } else {
                        Log.e("MovieRepository", "API Error: ${response.message()}")
                        movieDetailsLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    Log.e("MovieRepository", "Network error: ${t.message}")
                    movieDetailsLiveData.postValue(null)
                }
            })

        return movieDetailsLiveData
    }

}

>>>>>>> 31c47be (Initial commit)
