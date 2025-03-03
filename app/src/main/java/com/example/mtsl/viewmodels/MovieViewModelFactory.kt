package com.example.mtsl.viewmodels

<<<<<<< HEAD
import android.content.Context
=======
>>>>>>> 31c47be (Initial commit)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mtsl.repositorys.MovieRepository


<<<<<<< HEAD
@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(MovieRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
=======
class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
>>>>>>> 31c47be (Initial commit)
