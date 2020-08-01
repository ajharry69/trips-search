package com.hava.trips.ui.details;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.test.filters.MediumTest;

import com.hava.trips.HiltTestActivity;
import com.hava.trips.R;
import com.hava.trips.di.modules.DataSourceModule;
import com.hava.trips.utils.rules.IdlingResourceRule;
import com.hava.trips.utils.rules.NavigationControllerRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static com.hava.trips.TripData.TRIP;
import static com.hava.trips.utils.HiltContainer.launchFragmentInHiltContainer;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.Matchers.equalTo;

@MediumTest
@HiltAndroidTest
@UninstallModules(value = {DataSourceModule.class})
public class TripDetailsFragmentTest {

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

        Bundle args = new TripDetailsFragmentArgs.Builder(TRIP.getId()).build().toBundle();
        launchFragmentInHiltContainer(HiltTestActivity.class, TripDetailsFragment.class, args, R.style.Theme_HavaTrips, model -> {
            model.attachMapCallback = false;
            Navigation.setViewNavController(model.requireView(), navController);
            idlingResourceRule.dataBindingIdlingResource.monitorFragment(model);
        });
    }

    @Test
    public void clicking_backArrowButton_navigatesToTripResultsFragment() {
        onView(withContentDescription(R.string.content_desc_navigate_back))
                .check(matches(isDisplayed())).perform(click());

        NavDestination destination = requireNonNull(navController.getCurrentDestination());
        assertThat(destination.getId(), equalTo(R.id.dest_trip_results));
    }
}