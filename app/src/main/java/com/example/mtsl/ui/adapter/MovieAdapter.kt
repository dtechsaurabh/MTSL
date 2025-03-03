package com.example.mtsl.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtsl.R
import com.example.mtsl.models.Movie
import com.example.mtsl.databinding.ItemMovieBinding
import com.example.mtsl.db.MovieDao
import com.example.mtsl.ui.activity.movies.MovieDetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieAdapter(
    private var movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit,
    private val movieDao: MovieDao
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onMovieClick, movieDao)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (Movie) -> Unit,
        private val movieDao: MovieDao
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseYear.text = movie.release_date

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.moviePoster)

            updateFavoriteIcon(movie.isFavorite)

            binding.carditem.setOnClickListener { onMovieClick(movie) }

            binding.axisRelative.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                    putExtra("MOVIE_ID", movie.id)
                }
                context.startActivity(intent)
            }

            binding.favoriteButton.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                updateFavoriteIcon(movie.isFavorite)

                // Store full movie data in the database
                CoroutineScope(Dispatchers.IO).launch {
                    if (movie.isFavorite) {
                        movieDao.insertMovie(movie) // Store full movie object
                    } else {
                        movieDao.deleteMovie(movie) // Remove movie from favorites
                    }
                }
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            binding.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
        }
    }
}
