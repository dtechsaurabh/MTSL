package com.example.mtsl.utils

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
    }
}
