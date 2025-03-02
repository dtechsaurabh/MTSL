package com.example.mtsl.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mtsl.repositorys.MovieRepository


@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(MovieRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
