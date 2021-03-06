package com.augie.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.core.utils.EspressoIdlingResources
import com.augie.moviecatalogue.home.HomeActivity
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
        // careful this test could fail if the movie already in favorite before the test
        onView(withId(R.id.civ_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_border_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
    }

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
        // careful this test could fail if the movie already in favorite before the test
        onView(withId(R.id.civ_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_border_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
    }

    @Test
    fun loadFavoriteMovieNotEmpty() {
        // favorite the first item from favorite
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.civ_favorite)).perform(click())
        // back to home activity
        onView(isRoot()).perform(pressBack())

        // go to favorite activity
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))

        // change favorite state to default again
        onView(withId(R.id.rv_favorite_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.civ_favorite)).perform(click())
    }

    @Test
    fun loadFavoriteMovieEmpty() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.tv_no_movie_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTvShowNotEmpty() {
        // favorite the first item from favorite
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.civ_favorite)).perform(click())
        // back to home activity
        onView(isRoot()).perform(pressBack())

        // go to favorite activity
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_favorite_tv_show)).check(matches(isDisplayed()))

        // change favorite state to default again
        onView(withId(R.id.rv_favorite_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.civ_favorite)).perform(click())
    }

    @Test
    fun loadFavoriteTvShowEmpty() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.tv_no_tv_show_favorite)).check(matches(isDisplayed()))
    }
}