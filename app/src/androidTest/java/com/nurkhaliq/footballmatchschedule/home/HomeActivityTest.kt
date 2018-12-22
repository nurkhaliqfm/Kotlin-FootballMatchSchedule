package com.nurkhaliq.footballmatchschedule.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.nurkhaliq.footballmatchschedule.HomeActivity
import com.nurkhaliq.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(withId(list_event))
            .check(matches(isDisplayed()))
        onView(withId(list_event)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(withId(list_event)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))

        onView(withId(add_to_favorites))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorites)).perform(click())
        pressBack()

        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
    }
}