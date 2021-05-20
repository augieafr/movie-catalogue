package com.augie.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.utils.EspressoIdlingResources
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        //
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadTvShow() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )

        // check if the views are displayed
        onView(withId(R.id.img_detail_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_duration)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_genre)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
        // share icon
        onView(withId(R.id.civ_share)).check(matches(isDisplayed()))

        // favorite icon, also check image changes when clicked
        onView(withId(R.id.civ_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_border_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_24))))
    }

    // need to test tv show detail too because it has difference data even though has same UI and entity
    @Test
    fun loadDetailTvShow() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )

        // check if the view matches with the data
        onView(withId(R.id.img_detail_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_duration)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_genre)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))

        // share icon
        onView(withId(R.id.civ_share)).check(matches(isDisplayed()))

        // favorite icon, also check image changes when clicked
        onView(withId(R.id.civ_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_border_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_24))))
    }
}