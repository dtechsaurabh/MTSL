package com.example.mtsl.utils


import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun waitForView(viewMatcher: Matcher<View>, timeout: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
        override fun getDescription(): String = "Wait up to $timeout ms for view to appear"
        override fun perform(uiController: UiController, view: View?) {
            val startTime = System.currentTimeMillis()
            val endTime = startTime + timeout
            do {
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime && viewMatcher.matches(view).not())
        }
    }
}
