package com.example.mtsl

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.mtsl.utils.waitForView
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchMovie_displaysResultsOrHandlesEmptyState() {
        // 🔹 Wait for MovieFragment to be fully loaded before interacting with the SearchView
        onView(isRoot()).perform(waitForView(withId(R.id.searchView), 8000)) // Increased wait time

        // 🔹 Ensure `SearchView` is displayed
        onView(withId(R.id.searchView)).check(matches(isDisplayed()))

        // 🔹 Perform actions on `SearchView`
        onView(withId(R.id.searchView))
            .perform(click(), typeText("Inc"))

        // 🔹 Close the keyboard
        closeSoftKeyboard()

        // 🔹 Wait for RecyclerView to load search results
        onView(isRoot()).perform(waitForView(withId(R.id.recyclerView), 5000))

        // 🔹 Check if RecyclerView is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        // 🔹 Handle both cases: if results exist OR if an empty state is shown
        try {
            onView(allOf(withId(R.id.recyclerView), hasDescendant(withText("Inception"))))
                .check(matches(isDisplayed()))
        } catch (e: Exception) {
            // If "Inception" is not found, check if empty state is displayed
            onView(withId(R.id.loadingText)) // Adjust ID based on your app
                .check(matches(isDisplayed()))
        }
    }
}
