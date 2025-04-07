package com.example.mtsl.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtsl.databinding.FragmentFavoritesBinding
import com.example.mtsl.models.Movie
import com.example.mtsl.ui.adapter.FavoriteAdapter
import com.example.mtsl.viewmodels.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(
            mutableListOf(),
            onMovieClick = { movie ->
                Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
            },
            onRemoveFavorite = { movie ->
                removeFavoriteMovie(movie)
            }
        )

        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewFavorites.adapter = favoriteAdapter
    }

    fun observeFavorites() {
        favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            val safeMovies = movies ?: emptyList()
            favoriteAdapter.updateMovies(safeMovies)

            // ✅ Show empty message when list is empty
            if (safeMovies.isEmpty()) {
                binding.tvEmptyMessage.visibility = View.VISIBLE
            } else {
                binding.tvEmptyMessage.visibility = View.GONE
            }
        }
    }

    fun removeFavoriteMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteViewModel.removeFavorite(movie) // Call ViewModel function
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
