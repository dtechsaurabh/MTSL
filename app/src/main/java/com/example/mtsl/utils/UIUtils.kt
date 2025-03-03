package com.example.mtsl.utils

<<<<<<< HEAD

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
=======
import android.view.View
import androidx.viewbinding.ViewBinding

object UIUtils {
    fun showLoading(binding: ViewBinding, progressBar: View, loadingText: View) {
        progressBar.visibility = View.VISIBLE
        if (loadingText is android.widget.TextView) {
            loadingText.text = "Please wait..."
        }
        loadingText.visibility = View.VISIBLE
    }

    fun hideLoading(binding: ViewBinding, progressBar: View, loadingText: View) {
        progressBar.visibility = View.GONE
        loadingText.visibility = View.GONE
    }

    fun showNoInternet(binding: ViewBinding, progressBar: View, loadingText: View) {
        if (loadingText is android.widget.TextView) {
            loadingText.text = "No Internet Connection"
        }
        loadingText.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun showNoMoviesFound(binding: ViewBinding, progressBar: View, loadingText: View) {
        if (loadingText is android.widget.TextView) {
            loadingText.text = "No movies found"
        }
        loadingText.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
>>>>>>> 31c47be (Initial commit)
    }
}
