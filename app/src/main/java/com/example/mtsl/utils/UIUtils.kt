package com.example.mtsl.utils


import android.view.View
import com.example.mtsl.databinding.ActivityMovieBinding
import com.example.mtsl.databinding.FragmentActivityMovieBinding

object UIUtils {
    fun showLoading(binding: FragmentActivityMovieBinding) {
        binding.progressBar.visibility = View.VISIBLE
        binding.loadingText.text = "Please wait..."
        binding.loadingText.visibility = View.VISIBLE
    }

    fun hideLoading(binding: FragmentActivityMovieBinding) {
        binding.progressBar.visibility = View.GONE
        binding.loadingText.visibility = View.GONE
    }

    fun showNoInternet(binding: FragmentActivityMovieBinding) {
        binding.loadingText.text = "No Internet Connection"
        binding.loadingText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    fun showNoMoviesFound(binding: FragmentActivityMovieBinding) {
        binding.loadingText.text = "No movies found"
        binding.loadingText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}
