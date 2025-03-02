package com.example.mtsl.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtsl.R
import com.example.mtsl.databinding.ItemMovieBinding
import com.example.mtsl.models.Movie

class MovieAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (Movie) -> Unit,
    private val onFavoriteClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseYear.text = movie.releaseDate

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(binding.moviePoster)

            // Ensure the favorite icon updates correctly
            updateFavoriteIcon(movie.isFavorite)

            binding.root.setOnClickListener { onMovieClick(movie) }

            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(movie) // Ask ViewModel to update state
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            binding.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
        }
    }
}
