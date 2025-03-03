package com.example.mtsl.ui.adapter

<<<<<<< HEAD
=======
import android.content.Intent
import android.util.Log
>>>>>>> 31c47be (Initial commit)
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtsl.R
<<<<<<< HEAD
import com.example.mtsl.databinding.ItemMovieBinding
import com.example.mtsl.models.Movie

class MovieAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (Movie) -> Unit,
    private val onFavoriteClick: (Movie) -> Unit
=======
import com.example.mtsl.models.Movie
import com.example.mtsl.databinding.ItemMovieBinding
import com.example.mtsl.ui.activity.movies.MovieDetailsActivity


class MovieAdapter(
    private var movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
>>>>>>> 31c47be (Initial commit)
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
<<<<<<< HEAD
        return MovieViewHolder(binding)
=======
        return MovieViewHolder(binding, onMovieClick)
>>>>>>> 31c47be (Initial commit)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
<<<<<<< HEAD
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
=======
        movies = newMovies
        notifyDataSetChanged() // Notify adapter when new data is set
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseYear.text = movie.release_date

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.moviePoster)

            // ✅ Show correct favorite icon initially
            updateFavoriteIcon(movie.isFavorite)

            binding.carditem.setOnClickListener { onMovieClick(movie) }
//            binding.axisRelative.setOnClickListener {
//                val context = binding.root.context
//                Log.d("MovieClick", "Clicked on movie: ${movie.title}")  // ✅ Debug log
//                val intent = Intent(context, MovieDetailsActivity::class.java).apply {
//                    putExtra("MOVIE_EXTRA", movie)
//                }
//                context.startActivity(intent)
//            }
            binding.axisRelative.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                    putExtra("MOVIE_ID", movie.id)  // Ensure correct key
                }
                context.startActivity(intent)
            }

            binding.favoriteButton.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                updateFavoriteIcon(movie.isFavorite)
>>>>>>> 31c47be (Initial commit)
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            binding.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
        }
<<<<<<< HEAD
=======

>>>>>>> 31c47be (Initial commit)
    }
}
