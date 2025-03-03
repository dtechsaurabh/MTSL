package com.example.mtsl.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtsl.R
import com.example.mtsl.databinding.ItemMovieBinding
import com.example.mtsl.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (Movie) -> Unit,
    private val onRemoveFavorite: (Movie) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, onMovieClick, onRemoveFavorite)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (Movie) -> Unit,
        private val onRemoveFavorite: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseYear.text = movie.release_date

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.moviePoster)

            binding.favoriteButton.setImageResource(R.drawable.ic_favorite_filled) // ✅ Default filled icon

            binding.carditem.setOnClickListener { onMovieClick(movie) }

            binding.favoriteButton.setOnClickListener {
                onRemoveFavorite(movie) // ✅ Remove from DB when clicked
            }
        }
    }
}
