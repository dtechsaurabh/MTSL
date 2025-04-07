package com.example.mtsl


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mtsl.models.Movie
import com.example.mtsl.ui.adapter.FavoriteAdapter
import com.example.mtsl.ui.fragments.FavoritesFragment
import com.example.mtsl.viewmodels.FavoriteViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times

class FavoritesFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fragment: FavoritesFragment
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    @Before
    fun setup() {
        favoriteViewModel = mock(FavoriteViewModel::class.java)
        fragment = FavoritesFragment()
        favoriteAdapter = mock(FavoriteAdapter::class.java)
    }

    @Test
    fun `test observeFavorites updates adapter`() {
        val moviesLiveData = MutableLiveData<List<Movie>>()
        `when`(favoriteViewModel.favoriteMovies).thenReturn(moviesLiveData)

        fragment.observeFavorites()

        val sampleMovies = listOf(Movie(id = 1, title = "Inception", overview = "", release_date = "", poster_path = "", vote_average = 0.0F))
        moviesLiveData.postValue(sampleMovies)

        verify(favoriteAdapter, times(1)).updateMovies(sampleMovies)
    }

    @Test
    fun `test removeFavoriteMovie calls ViewModel`() {
        val movie = Movie(id = 1, title = "Inception", overview = "", release_date = "", poster_path = "", vote_average = 0.0F)

        fragment.removeFavoriteMovie(movie)

        val captor = argumentCaptor<Movie>()
        verify(favoriteViewModel, times(1)).removeFavorite(captor.capture())
        assert(captor.firstValue == movie)
    }

    @Test
    fun `test empty favorites list shows empty message`() {
        val moviesLiveData = MutableLiveData<List<Movie>>()
        `when`(favoriteViewModel.favoriteMovies).thenReturn(moviesLiveData)

        fragment.observeFavorites()

        moviesLiveData.postValue(emptyList())

        assert(fragment.binding.tvEmptyMessage.visibility == View.VISIBLE)

    }
}
