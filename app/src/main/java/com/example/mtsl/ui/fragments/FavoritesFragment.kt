package com.example.mtsl.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtsl.databinding.FragmentFavoritesBinding
import com.example.mtsl.ui.adapter.FavoriteAdapter
import com.example.mtsl.viewmodels.FavoriteViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!  // Use safe access
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

        // ✅ Initialize ViewModel
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(
            mutableListOf(),
            onMovieClick = { movie ->
                Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewFavorites.adapter = favoriteAdapter
    }

    private fun observeFavorites() {
        favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            val safeMovies = movies ?: emptyList() // ✅ Ensure no null crash
            favoriteAdapter.updateMovies(safeMovies)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // ✅ Prevent memory leaks
    }
}
