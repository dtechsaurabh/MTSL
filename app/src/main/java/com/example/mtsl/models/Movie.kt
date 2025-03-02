package com.example.mtsl.models


data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String?, // ✅ Ensure it matches FavoriteMovieEntity
    val posterPath: String?, // ✅ Ensure it matches FavoriteMovieEntity
    val voteAverage: Double?, // ✅ Ensure correct type
    var isFavorite: Boolean = false
)
