package com.example.mtsl.ui.activity.movies

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mtsl.R

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var favoriteButton: Button
    //private lateinit var database: MovieDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

      //  database = MovieDatabase.getDatabase(this)
      //  favoriteButton = findViewById(R.id.favoriteButton)

      //  val movie = intent.getParcelableExtra<Movie>("movie")

//        favoriteButton.setOnClickListener {
//            movie?.let {
//                val movieEntity = MovieEntity(it.id, it.title, it.overview, it.poster_path)
//                lifecycleScope.launch {
//                    database.movieDao().insertMovie(movieEntity)
//                    Toast.makeText(this@MovieDetailsActivity, "Added to Favorites", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }
}