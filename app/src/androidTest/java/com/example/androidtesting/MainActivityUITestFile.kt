package com.example.androidtesting

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityUITestFile {
    /*@get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)*/

    @Test
    fun activityUITestFillScreen() {
        val activityRule = ActivityScenario.launch(MainActivity::class.java)
//        onView(withId(R.id.text))
        onView(withId(R.id.this_is_Main_Activity)).check(matches(isDisplayed()))
    }

    @Test
    fun check_text_file_text() {
        val activity = ActivityScenario.launch(MainActivity::class.java)
        // Text File Account file
        onView(withId(R.id.text_file_txt)).check(matches(withText(R.string.fir_acct)))
        //Is Visible or NOT
        onView(withId(R.id.btn_click)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }




}