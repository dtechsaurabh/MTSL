package com.example.mtsl.utils

import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.mtsl.viewmodels.MovieViewModel
import com.example.mtsl.utils.UIUtils
import com.example.mtsl.databinding.ActivityMovieBinding
import com.example.mtsl.databinding.FragmentActivityMovieBinding

import androidx.viewbinding.ViewBinding


object SearchHelper {
    fun setupSearchView(
        binding: ViewBinding, // Accepts any ViewBinding
        searchView: SearchView, // Accepts any SearchView
        progressBar: View, // Pass progress bar separately
        loadingText: View, // Pass loading text separately
        movieViewModel: MovieViewModel
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    UIUtils.showLoading(binding, progressBar, loadingText) // Show loader
                    movieViewModel.searchMovies(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    UIUtils.showLoading(binding, progressBar, loadingText) // Show loader
                    movieViewModel.searchMovies(it)
                }
                return true
            }
        })

        // Refresh movies when search is closed
        searchView.setOnCloseListener {
            movieViewModel.fetchMovies()
            false
        }
    }
}



//object SearchHelper {
//    fun setupSearchView(binding: FragmentActivityMovieBinding, movieViewModel: MovieViewModel) {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    UIUtils.showLoading(binding) // Show loader
//                    movieViewModel.searchMovies(it)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let {
//                    UIUtils.showLoading(binding) // Show loader
//                    movieViewModel.searchMovies(it)
//                }
//                return true
//            }
//        })
//
//        // Refresh movies when search is closed
//        binding.searchView.setOnCloseListener {
//            movieViewModel.fetchMovies()
//            false
//        }
//    }
//}
