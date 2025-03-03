package com.example.mtsl.utils

<<<<<<< HEAD
=======
import android.view.View
>>>>>>> 31c47be (Initial commit)
import androidx.appcompat.widget.SearchView
import com.example.mtsl.viewmodels.MovieViewModel
import com.example.mtsl.utils.UIUtils
import com.example.mtsl.databinding.ActivityMovieBinding
import com.example.mtsl.databinding.FragmentActivityMovieBinding

<<<<<<< HEAD
object SearchHelper {
    fun setupSearchView(binding: FragmentActivityMovieBinding, movieViewModel: MovieViewModel) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    UIUtils.showLoading(binding) // Show loader
=======
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
>>>>>>> 31c47be (Initial commit)
                    movieViewModel.searchMovies(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
<<<<<<< HEAD
                    UIUtils.showLoading(binding) // Show loader
=======
                    UIUtils.showLoading(binding, progressBar, loadingText) // Show loader
>>>>>>> 31c47be (Initial commit)
                    movieViewModel.searchMovies(it)
                }
                return true
            }
        })

        // Refresh movies when search is closed
<<<<<<< HEAD
        binding.searchView.setOnCloseListener {
=======
        searchView.setOnCloseListener {
>>>>>>> 31c47be (Initial commit)
            movieViewModel.fetchMovies()
            false
        }
    }
}
<<<<<<< HEAD
=======



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
>>>>>>> 31c47be (Initial commit)
