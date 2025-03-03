package com.example.mtsl.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Int, // Ensure correct type
    val title: String,
    val overview: String,
    val poster_path: String
)