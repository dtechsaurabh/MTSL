package com.example.mtsl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
import com.example.mtsl.models.MovieResponse
import com.example.mtsl.repositorys.MovieRepository
import com.example.mtsl.retrofit.ApiInterface
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiInterface
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiInterface::class.java)
        movieRepository = MovieRepository()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch popular movies successfully`() = runBlockingTest {
        val jsonResponse = """
            {
                "results": [
                    { "id": 1, "title": "Movie One", "poster_path": "/path1.jpg", "release_date": "2023-01-01", "vote_average": 8.5 },
                    { "id": 2, "title": "Movie Two", "poster_path": "/path2.jpg", "release_date": "2023-02-01", "vote_average": 7.8 }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        val liveDataResult = movieRepository.getPopularMovies()
        val result = getLiveDataValue(liveDataResult)

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals("Movie One", result[0].title)
    }

    @Test
    fun `fetch popular movies returns empty list`() = runBlockingTest {
        val emptyResponse = """{ "results": [] }"""
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(emptyResponse))

        val liveDataResult = movieRepository.getPopularMovies()
        val result = getLiveDataValue(liveDataResult)

        assertNotNull(result)
        assertEquals(0, result.size)
    }

    @Test
    fun `fetch popular movies returns error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val result = movieRepository.getPopularMovies().getOrAwaitValue()

        assertNotNull(result)
        assertEquals(0, result.size)
    }
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data = t
                this@getOrAwaitValue.removeObserver(this)
                latch.countDown()
            }
        }
        this.observeForever(observer)

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        return data ?: throw NullPointerException("LiveData value is null")
    }

    // Helper function to get value from LiveData synchronously
    private fun <T> getLiveDataValue(liveData: LiveData<T>): T {
        val latch = CountDownLatch(1)
        val data = arrayOfNulls<Any>(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }
}
