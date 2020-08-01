package com.hava.trips;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.hava.trips.di.modules.DataSourceModule;
import com.hava.trips.utils.rules.IdlingResourceRule;
import com.hava.trips.utils.rules.NavigationControllerRule;

import org.junit.Before;
import org.junit.Rule;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

@LargeTest
@HiltAndroidTest
@UninstallModules(value = {DataSourceModule.class})
public class MainActivityTest {

    @Rule
    public HiltAndroidRule hiltAndroidRule = new HiltAndroidRule(this);
    @Rule
    public NavigationControllerRule navigationControllerRule = new NavigationControllerRule();
    @Rule
    public IdlingResourceRule idlingResourceRule = new IdlingResourceRule();
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        hiltAndroidRule.inject();
        idlingResourceRule.dataBindingIdlingResource.monitorActivity(activityScenarioRule.getScenario());
    }

}