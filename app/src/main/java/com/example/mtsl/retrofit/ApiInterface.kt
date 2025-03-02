package com.example.mtsl.retrofit

import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
import com.example.mtsl.models.Movie
import com.example.mtsl.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Call

interface ApiInterface {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String = ApiConstants.API_KEY,
        @Query("query") query: String
    ): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ): Call<Movie>
}