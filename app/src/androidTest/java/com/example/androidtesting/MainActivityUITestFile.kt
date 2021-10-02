package com.example.androidtesting


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityUITestFile {

    @Test
    fun get_click_event_to_nag_to_netActivity() {
        val activity= ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btn_click)).perform(click())
        onView(withId(R.id.next_Activity_file)).check(matches(isDisplayed()))
    }

    @Test
    fun press_back_to_main_Activity() {
        val activity=ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btn_click)).perform(click())
        onView(withId(R.id.next_Activity_file)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.this_is_Main_Activity)).check(matches(isDisplayed()))
    }
}