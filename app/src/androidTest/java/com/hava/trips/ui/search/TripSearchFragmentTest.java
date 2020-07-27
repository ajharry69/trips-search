package com.hava.trips.ui.search;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hava.trips.utils.HiltContainer.launchFragmentInHiltContainer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@MediumTest
@HiltAndroidTest
@UninstallModules(value = {DataSourceModule.class})
public class TripSearchFragmentTest {

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

        launchFragmentInHiltContainer(TripSearchFragment.class, null, R.style.Theme_HavaTrips, model ->
                Navigation.setViewNavController(model.requireView(), navController));
        // Used to make Espresso work with DataBinding. Without it the tests will be flaky because
        // DataBinding uses Choreographer class to synchronize its view updates hence using this to
        // monitor a launched fragment in fragment scenario will make Espresso wait before doing
        // additional checks
        // TODO: Comment line below and provide an additional argument of FragmentScenario instance
        // idlingResourceRule.dataBindingIdlingResource.monitorFragment(FragmentScenario);
    }

    @Test
    public void viewsAreInTheCorrectStateByDefault() {
        onView(withId(R.id.include_cancelled)).check(matches(isNotChecked()));
        onView(withId(R.id.distance_any)).check(matches(isChecked()));
        onView(withId(R.id.time_any)).check(matches(isChecked()));
    }

    @Test
    public void clicking_includeCancelledSwitchTextLabel_togglesSwitch() {
        onView(withId(R.id.include_cancelled_label)).perform(click())
                .check(matches(isChecked()));
        onView(withId(R.id.include_cancelled_label)).perform(click())
                .check(matches(isNotChecked()));
    }

    @Test
    public void clicking_searchButton_navigatesToTripResultsFragment() {
        onView(withId(R.id.search)).perform(click());

        NavDestination destination = navController.getCurrentDestination();
        if (destination != null) assertThat(destination.getId(), equalTo(R.id.dest_trip_results));
    }

}