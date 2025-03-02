package com.example.mtsl.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): LiveData<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: FavoriteMovieEntity)

    @Delete
    suspend fun removeFavorite(movie: FavoriteMovieEntity)

    @Query("SELECT COUNT(*) FROM favorite_movies WHERE id = :movieId")
    suspend fun isFavorite(movieId: Int): Int

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMoviesList(): List<FavoriteMovieEntity> // ✅ Fix: Correct return type

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<FavoriteMovieEntity>) // ✅ Fix: Remove nullable type `?`
}
