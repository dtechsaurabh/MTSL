package com.example.mtsl.models

<<<<<<< HEAD
=======
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
>>>>>>> 31c47be (Initial commit)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
<<<<<<< HEAD
    val releaseDate: String?, // ✅ Ensure it matches FavoriteMovieEntity
    val posterPath: String?, // ✅ Ensure it matches FavoriteMovieEntity
    val voteAverage: Double?, // ✅ Ensure correct type
    var isFavorite: Boolean = false
)
=======
    val release_date: String,
    val poster_path: String,
    val vote_average: Float,
    var isFavorite: Boolean = false

) : Parcelable
>>>>>>> 31c47be (Initial commit)
