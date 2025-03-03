package com.example.mtsl.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtsl.databinding.FragmentSearchBinding
=======
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtsl.databinding.FragmentSearchBinding
import com.example.mtsl.repositorys.MovieRepository
import com.example.mtsl.ui.adapter.MovieAdapter
import com.example.mtsl.utils.SearchHelper
import com.example.mtsl.viewmodels.MovieViewModel
import com.example.mtsl.viewmodels.MovieViewModelFactory
>>>>>>> 31c47be (Initial commit)

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

<<<<<<< HEAD
=======
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository()) // Consider using DI (Hilt/Koin)
    }

    private lateinit var movieAdapter: MovieAdapter

>>>>>>> 31c47be (Initial commit)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
<<<<<<< HEAD
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // TODO: Set adapter when ready (binding.recyclerView.adapter = yourAdapter)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // TODO: Handle search submission
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO: Handle live search filtering
                return true
            }
        })
=======
        observeMovies()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(
            mutableListOf(),
            onMovieClick = { movie ->
                Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2) // Grid with 2 columns
            adapter = movieAdapter
        }
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

    private fun observeMovies() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty()) {
                binding.loadingText.text = "No movies found"
                binding.loadingText.visibility = View.VISIBLE
            } else {
                binding.loadingText.visibility = View.GONE
                movieAdapter.updateMovies(movies) // Ensure your adapter has this method
            }
        }
>>>>>>> 31c47be (Initial commit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
