package com.example.mtsl.ui.activity.movies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mtsl.databinding.ActivityMovieDetailsBinding
import com.example.mtsl.models.MovieDetails
import com.example.mtsl.repositorys.MovieRepository
import com.example.mtsl.viewmodels.MovieViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        if (movieId == -1) {
            Log.e("MovieDetailsActivity", "Invalid movie ID")
            finish()
            return
        }

        val repository = MovieRepository()
        movieViewModel = ViewModelProvider(this, MovieViewModel.Factory(repository)).get(MovieViewModel::class.java)

        movieViewModel.fetchMovieDetails(movieId)
        observeMovieDetails()
    }

    private fun observeMovieDetails() {
        movieViewModel.movieDetails.observe(this) { movie ->
            movie?.let { updateUI(it) }
        }
    }

    private fun updateUI(movie: MovieDetails) {
        binding.apply {
            movieTitle.text = movie.title
            movieReleaseDate.text = "Release Date: ${movie.releaseDate}"
            movieRating.text = "Rating: ${movie.voteAverage} (${movie.voteCount} votes)"
            movieOverview.text = movie.overview
            movieGenres.text = movie.genres.joinToString { it.name }
            movieTagline.text = movie.tagline ?: ""

            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(moviePoster)

            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
                .into(movieBackdrop)
        }
    }
}
