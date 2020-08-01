package com.hava.trips.ui.results;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.test.filters.MediumTest;

import com.hava.trips.HiltTestActivity;
import com.hava.trips.R;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.di.modules.DataSourceModule;
import com.hava.trips.utils.rules.IdlingResourceRule;
import com.hava.trips.utils.rules.NavigationControllerRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hava.trips.utils.HiltContainer.launchFragmentInHiltContainer;
import static com.hava.trips.utils.ViewUtils.clickRecyclerViewItemWithId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@MediumTest
@HiltAndroidTest
@UninstallModules(value = {DataSourceModule.class})
public class TripResultsFragmentTest {

    @Rule
    public HiltAndroidRule hiltAndroidRule = new HiltAndroidRule(this);
    @Rule
    public NavigationControllerRule navigationControllerRule = new NavigationControllerRule();
    @Rule
    public IdlingResourceRule idlingResourceRule = new IdlingResourceRule();

    private NavController navController;

    @Before
    public void setUp() {
        hiltAndroidRule.inject();
        navController = navigationControllerRule.navController;

        Bundle args = new TripResultsFragmentArgs.Builder(FilterParams.DEFAULT).build().toBundle();
        launchFragmentInHiltContainer(HiltTestActivity.class, TripResultsFragment.class, args, R.style.Theme_HavaTrips, model -> {
            Navigation.setViewNavController(model.requireView(), navController);
            idlingResourceRule.dataBindingIdlingResource.monitorFragment(model);
        });
    }

    @Test
    public void clicking_backArrowButton_navigatesToTripSearchFragment() {
        onView(withContentDescription(R.string.content_desc_navigate_back))
                .check(matches(isDisplayed())).perform(click());

        NavDestination destination = Objects.requireNonNull(navController.getCurrentDestination());
        assertThat(destination.getId(), equalTo(R.id.dest_trip_search));
    }

    @Test
    public void clicking_tripItem_navigatesToTripDetailsFragment() {
        onView(withId(R.id.trips)).perform(actionOnItemAtPosition(0, click()));

        NavDestination destination = Objects.requireNonNull(navController.getCurrentDestination());
        assertThat(destination.getId(), equalTo(R.id.dest_trip_details));
    }

    @Test
    public void clicking_tripItemArrowButton_navigatesToTripDetailsFragment() {
        onView(withId(R.id.trips))
                .perform(actionOnItemAtPosition(0, clickRecyclerViewItemWithId(R.id.options)));

        NavDestination destination = Objects.requireNonNull(navController.getCurrentDestination());
        assertThat(destination.getId(), equalTo(R.id.dest_trip_details));
    }
}