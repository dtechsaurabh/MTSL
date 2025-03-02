package com.example.mtsl.db


import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.models.Movie

fun Movie.toFavoriteEntity(): FavoriteMovieEntity? {
    return this.posterPath?.let {
        this.releaseDate?.let { it1 ->
            FavoriteMovieEntity(
            id = this.id,  // ✅ Ensure 'id' exists in Movie class
            title = this.title,  // ✅ Ensure 'title' exists in Movie class
            posterPath = it,  // ✅ Ensure 'poster_path' exists
            releaseDate = it1  // ✅ Ensure 'release_date' exists
        )
        }
    }
}
