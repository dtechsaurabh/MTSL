package com.example.mtsl.db

import com.example.mtsl.models.Movie




fun FavoriteMovieEntity.toDomainMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,  // ✅ Matches Movie.kt
        releaseDate = this.releaseDate,  // ✅ Matches Movie.kt
        overview = "",  // Database doesn’t store this, so keep it empty
        voteAverage = 0.0,  // ✅ Default value for rating
        isFavorite = true
    )
}
