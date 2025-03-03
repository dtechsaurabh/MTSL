package com.example.mtsl.repositorys

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.agatsa.sanketriskusers.network.api.RetrofitProvider
import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieDetails
import com.example.mtsl.models.MovieResponse


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

