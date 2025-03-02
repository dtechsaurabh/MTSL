package com.example.mtsl.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtsl.databinding.FragmentFavoritesBinding
import com.example.mtsl.db.FavoriteMovieEntity
import com.example.mtsl.models.Movie
import com.example.mtsl.ui.adapter.FavoritesAdapter
import com.example.mtsl.viewmodels.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        favoritesAdapter = FavoritesAdapter(
            mutableListOf(),
            onMovieClick = { movie ->
                // Handle movie click (Navigate to details)
            },
            onFavoriteClick = { movie ->
                if (movie.isFavorite) {
                    // Remove from favorites
//                    viewModel.removeFavorite(
//                        FavoriteMovieEntity(
//                            movie.id, movie.title, movie.overview, movie.release_date,
//                            movie.poster_path, movie.vote_average, false
//                        )
//                    )
                } else {
                    // Add to favorites
//                    viewModel.addFavorite(
//                        FavoriteMovieEntity(
//                            movie.id, movie.title, movie.overview, movie.release_date,
//                            movie.poster_path, movie.vote_average, true
//                        )
//                    )
                }
            }

        )

        binding.recyclerViewFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
        }

        // Observe favorite movies
        viewModel.favoriteMovies.observe(viewLifecycleOwner) { favoriteMovies ->
            if (favoriteMovies.isNullOrEmpty()) {
                binding.recyclerViewFavorites.visibility = View.GONE
                binding.tvEmptyMessage.visibility = View.VISIBLE
            } else {
                binding.recyclerViewFavorites.visibility = View.VISIBLE
                binding.tvEmptyMessage.visibility = View.GONE


//                favoritesAdapter.updateMovies(favoriteMovies.map {
//                    Movie(
//                        it.id,
//                        it.title,
//                        it.poster_path,
//                        it.release_date,
//                        it.overview, // Correct field
//                        "0.0", // Placeholder for vote_average (add the actual value if available)
//                        true
//                    )
//                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
