package com.example.mtsl.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtsl.databinding.FragmentSearchBinding
import com.example.mtsl.db.MovieDao
import com.example.mtsl.db.MovieDatabase
import com.example.mtsl.repositorys.MovieRepository
import com.example.mtsl.ui.adapter.MovieAdapter
import com.example.mtsl.utils.SearchHelper
import com.example.mtsl.viewmodels.MovieViewModel
import com.example.mtsl.viewmodels.MovieViewModelFactory


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    val binding get() = _binding!!

    val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository()) // Consider using DI (Hilt/Koin)
    }
    private lateinit var movieDao: MovieDao  // Add this

    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        movieDao = MovieDatabase.getDatabase(requireContext()).movieDao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        observeMovies()
    }

//    private fun setupRecyclerView() {
//        movieAdapter = MovieAdapter(
//            mutableListOf(),
//            onMovieClick = { movie ->
//                Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
//            }
//        )
//
//        binding.recyclerView.apply {
//            layoutManager = GridLayoutManager(requireContext(), 2) // Grid with 2 columns
//            adapter = movieAdapter
//        }
//    }
fun setupRecyclerView() {
    movieAdapter = MovieAdapter(
        mutableListOf(),
        onMovieClick = { movie ->
            Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
        },
        movieDao = movieDao  // ✅ Pass MovieDao here
    )

    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns grid
    binding.recyclerView.adapter = movieAdapter
}

    private fun setupSearchView() {
        SearchHelper.setupSearchView(
            binding,
            binding.searchView,
            binding.progressBar,
            binding.loadingText,
            movieViewModel
        )
    }

    fun observeMovies() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty()) {
                binding.loadingText.text = "No movies found"
                binding.loadingText.visibility = View.VISIBLE
            } else {
                binding.loadingText.visibility = View.GONE
                movieAdapter.updateMovies(movies) // Ensure your adapter has this method
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
