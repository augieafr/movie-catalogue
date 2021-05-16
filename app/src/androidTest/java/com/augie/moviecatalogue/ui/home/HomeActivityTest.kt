package com.augie.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.utils.DataDummy
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    private val dummyMovie = DataDummy.generateDummyMovie()
    private val dummyTvShow = DataDummy.generateDummyTvShow()

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovie.size
            )
        )
    }

    @Test
    fun loadTvShow() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShow.size
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

        // check if the view matches with the data
        onView(withId(R.id.img_detail_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.img_detail_poster)).check(
            matches(
                withTagValue(
                    equalTo(
                        dummyMovie[0].poster
                    )
                )
            )
        )

        onView(withId(R.id.tv_detail_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_duration)).check(matches(withText(dummyMovie[0].duration)))

        onView(withId(R.id.tv_detail_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_genre)).check(matches(withText(dummyMovie[0].genre)))

        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview)).check(matches(withText(dummyMovie[0].overview)))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
        // don't know how to get string resource in instrumental test, but this hard code way is work
        onView(withId(R.id.tv_detail_title)).check(matches(withText("${dummyMovie[0].title} (${dummyMovie[0].releaseDate})")))

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
        onView(withId(R.id.img_detail_poster)).check(
            matches(
                withTagValue(
                    equalTo(
                        dummyTvShow[0].poster
                    )
                )
            )
        )

        onView(withId(R.id.tv_detail_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_duration)).check(matches(withText(dummyTvShow[0].duration)))

        onView(withId(R.id.tv_detail_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_genre)).check(matches(withText(dummyTvShow[0].genre)))

        onView(withId(R.id.tv_detail_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_overview)).check(matches(withText(dummyTvShow[0].overview)))

        onView(withId(R.id.tv_detail_title)).check(matches(isDisplayed()))
        // don't know how to get string resource in instrumental test, but this hard code way is work
        onView(withId(R.id.tv_detail_title)).check(matches(withText("${dummyTvShow[0].title} (${dummyTvShow[0].releaseDate})")))

        // share icon
        onView(withId(R.id.civ_share)).check(matches(isDisplayed()))

        // favorite icon, also check image changes when clicked
        onView(withId(R.id.civ_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_border_24))))
        onView(withId(R.id.civ_favorite)).perform(click())
        onView(withId(R.id.civ_favorite)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_favorite_24))))
    }
}