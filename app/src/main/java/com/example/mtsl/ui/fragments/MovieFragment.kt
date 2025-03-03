package com.example.mtsl.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mtsl.databinding.FragmentActivityMovieBinding
import com.example.mtsl.db.MovieDao
import com.example.mtsl.db.MovieDatabase
import com.example.mtsl.repositorys.MovieRepository
import com.example.mtsl.ui.adapter.MovieAdapter
import com.example.mtsl.utils.NetworkUtils
import com.example.mtsl.utils.SearchHelper
import com.example.mtsl.utils.UIUtils
import com.example.mtsl.viewmodels.MovieViewModel
import com.example.mtsl.viewmodels.MovieViewModelFactory

class MovieFragment : Fragment() {

    private var _binding: FragmentActivityMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieRepository()) // No Hilt, manually creating repository
    }
    private lateinit var movieDao: MovieDao  // Add this

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityMovieBinding.inflate(inflater, container, false)
        movieDao = MovieDatabase.getDatabase(requireContext()).movieDao()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        SearchHelper.setupSearchView(
            binding,
            binding.searchView,
            binding.progressBar,
            binding.loadingText,
            movieViewModel
        )

        observeMovies()
        fetchMovies()


        // Fetch movies when the activity starts
    }


//    private fun setupRecyclerView() {
//        movieAdapter = MovieAdapter(
//            mutableListOf(),
//            onMovieClick = { movie ->
//                Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
//            }
//        )
//
//        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns grid
//        binding.recyclerView.adapter = movieAdapter
//    }

    private fun setupRecyclerView() {
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
    private fun observeMovies() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            Log.d("MovieFragmentLog", "Movies updated: ${movies.size}")

            UIUtils.hideLoading(binding, binding.progressBar, binding.loadingText)


            if (movies.isNotEmpty()) {
                movieAdapter.updateMovies(movies.toMutableList())
            } else {
                Log.d("MovieFragmentLog", "No movies found in ViewModel")
                UIUtils.showNoMoviesFound(binding, binding.progressBar, binding.loadingText)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchMovies() {
        Log.d("MovieFragmentLog", "fetchMovies() called")

        if (NetworkUtils.isInternetAvailable(requireContext())) {
            Log.d("MovieFragmentLog", "Internet is available, fetching movies...")
            UIUtils.showLoading(binding, binding.progressBar, binding.loadingText)

            movieViewModel.fetchMovies()
        } else {
            Log.d("MovieFragmentLog", "No internet connection")
            Toast.makeText(requireContext(), "No Internet Connection!", Toast.LENGTH_LONG).show()
            UIUtils.showNoInternet(binding, binding.progressBar, binding.loadingText)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
