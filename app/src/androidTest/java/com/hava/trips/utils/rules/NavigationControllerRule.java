package com.hava.trips.utils.rules;

import androidx.annotation.NavigationRes;
import androidx.navigation.NavController;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;

import com.hava.trips.R;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Creates a test NavController at the start of evey test
 */
public class NavigationControllerRule extends TestWatcher {
    public NavController navController;
    @NavigationRes
    private final int navGraphResId;

    public NavigationControllerRule(@NavigationRes int navGraphResId) {
        this.navGraphResId = navGraphResId;
    }

    public NavigationControllerRule() {
        this(R.navigation.nav_graph);
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
        navController.setGraph(navGraphResId);
    }
}
