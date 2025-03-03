<<<<<<< HEAD
//package com.example.mtsl.db
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface MovieDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovie(movie: MovieEntity)
//
//    @Delete
//    suspend fun deleteMovie(movie: MovieEntity)
//
//    @Query("SELECT * FROM favorite_movies")
//    suspend fun getAllMovies(): List<MovieEntity>
//}
=======
package com.example.mtsl.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllMovies(): List<MovieEntity>
}
>>>>>>> 31c47be (Initial commit)
