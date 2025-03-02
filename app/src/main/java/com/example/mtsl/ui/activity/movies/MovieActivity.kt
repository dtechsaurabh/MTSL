package com.example.mtsl.ui.activity.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mtsl.R
import com.example.mtsl.databinding.ActivityMovieBinding
import com.example.mtsl.ui.fragments.FavoritesFragment
import com.example.mtsl.ui.fragments.MovieFragment
import com.example.mtsl.ui.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // Default fragment on launch
        if (savedInstanceState == null) {
            loadFragment(MovieFragment())
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> {
                    loadFragment(MovieFragment())
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.nav_favorites -> {
                    loadFragment(FavoritesFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
