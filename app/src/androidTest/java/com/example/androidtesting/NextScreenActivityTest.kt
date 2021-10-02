package com.example.androidtesting

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NextScreenActivityTest {

    /*@get:Rule
    val activity=ActivityScenarioRule(NextScreenActivity::class.java)*/

    @Test
    fun title_is_displayed() {
        val activity=ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btn_click)).perform(click())
        onView(withId(R.id.text_file_Two_txt)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(R.string.sec_act)))
        }
    }

    @Test
    fun next_activity_display() {
        val activity=ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btn_click)).perform(click())
        onView(withId(R.id.next_Activity_file)).check(matches(isDisplayed()))
    }
}