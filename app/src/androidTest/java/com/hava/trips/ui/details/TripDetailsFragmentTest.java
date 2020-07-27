package com.hava.trips.ui.details;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.test.filters.MediumTest;

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
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static com.hava.trips.TripData.TRIP;
import static com.hava.trips.utils.HiltContainer.launchFragmentInHiltContainer;
import static org.hamcrest.MatcherAssert.assertThat;
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
        launchFragmentInHiltContainer(TripDetailsFragment.class, args, R.style.Theme_HavaTrips, model ->
                Navigation.setViewNavController(model.requireView(), navController));
        // Used to make Espresso work with DataBinding. Without it the tests will be flaky because
        // DataBinding uses Choreographer class to synchronize its view updates hence using this to
        // monitor a launched fragment in fragment scenario will make Espresso wait before doing
        // additional checks
        // TODO: Comment line below and provide an additional argument of FragmentScenario instance
        // idlingResourceRule.dataBindingIdlingResource.monitorFragment(FragmentScenario);
    }

    @Test
    public void clicking_backArrowButton_navigatesToTripResultsFragment() {
//        monitorFragment(dataBindingIdlingResource, FragmentScenario);
        onView(withContentDescription(R.string.content_desc_navigate_back)).perform(click());

        NavDestination destination = navController.getCurrentDestination();
        if (destination != null) assertThat(destination.getId(), equalTo(R.id.dest_trip_results));
    }
}