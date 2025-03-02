package com.example.mtsl.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtsl.R
import com.example.mtsl.databinding.ItemMovieBinding
import com.example.mtsl.models.Movie

@Suppress("DEPRECATION")
class FavoritesAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (Movie) -> Unit,
    private val onFavoriteClick: (Movie) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class FavoritesViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseYear.text = movie.releaseDate

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.moviePoster)

            binding.favoriteButton.setImageResource(
                if (movie.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )

            binding.root.setOnClickListener { onMovieClick(movie) }
            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(movie)
                notifyItemChanged(adapterPosition)
            }
        }
    }
}
