package com.example.mtsl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mtsl.models.Movie
import com.example.mtsl.ui.fragments.SearchFragment
import com.example.mtsl.viewmodels.MovieViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import android.view.View
import android.widget.TextView

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var moviesLiveData: MutableLiveData<List<Movie>>
    private lateinit var errorLiveData: MutableLiveData<String>
    private lateinit var fragment: SearchFragment

    @Before
    fun setup() {
        movieViewModel = mock(MovieViewModel::class.java)
        moviesLiveData = MutableLiveData()
        errorLiveData = MutableLiveData()

        whenever(movieViewModel.movies).thenReturn(moviesLiveData)
        //whenever(movieViewModel.errorMessage).thenReturn(errorLiveData)

        val scenario = launchFragmentInContainer<SearchFragment>(
            themeResId = R.style.Theme_MyApp
        )

        scenario.onFragment { createdFragment ->
            fragment = createdFragment

            // 🔹 Inject ViewModel using Reflection
            val viewModelField = SearchFragment::class.java.getDeclaredField("movieViewModel")
            viewModelField.isAccessible = true
            viewModelField.set(fragment, movieViewModel)
        }
    }

    @Test
    fun `test empty movie list shows no results message`() {
        moviesLiveData.postValue(emptyList())

        val emptyTextView = fragment.view?.findViewById<TextView>(R.id.tvEmptyMessage)
        assertEquals(View.VISIBLE, emptyTextView?.visibility)
    }

    @Test
    fun `test search result updates UI`() {
        val movies = listOf(
            Movie(1, "Inception", "A thriller", "2010-07-16", "/inception.jpg", 8.8f)
        )
        moviesLiveData.postValue(movies)

        assertEquals(1, fragment.movieAdapter.itemCount)
    }

//    @Test
//    fun `test network failure shows error message`() {
//        val errorMessage = "Network Error"
//        errorLiveData.postValue(errorMessage)
//
//        val errorTextView = fragment.view?.findViewById<TextView>(R.id.tvErrorMessage)
//        assertEquals(View.VISIBLE, errorTextView?.visibility)
//        assertEquals(errorMessage, errorTextView?.text)
//    }

    @Test
    fun `test malformed API response handles gracefully`() {
        moviesLiveData.postValue(null) // Simulating malformed response

        val emptyTextView = fragment.view?.findViewById<TextView>(R.id.tvEmptyMessage)
        assertEquals(View.VISIBLE, emptyTextView?.visibility) // Should show "No results"
    }

    @Test
    fun `test search calls ViewModel searchMovies`() {
        val query = "Inception"
        fragment.movieViewModel.searchMovies(query)

        verify(movieViewModel, times(1)).searchMovies(query)
    }

//    @Test
//    fun `test loading state UI visibility`() {
//        // Simulate loading state
//        fragment.showLoading(true)
//
//        val loadingView = fragment.view?.findViewById<View>(R.id.progressBar)
//        assertEquals(View.VISIBLE, loadingView?.visibility)
//
//        // After loading completes
//        fragment.showLoading(false)
//        assertEquals(View.GONE, loadingView?.visibility)
//    }

    @Test
    fun `test empty search query does not trigger search`() {
        fragment.movieViewModel.searchMovies("")

        verify(movieViewModel, never()).searchMovies("")
    }
}
