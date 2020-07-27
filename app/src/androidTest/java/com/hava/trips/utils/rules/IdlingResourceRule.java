package com.hava.trips.utils.rules;

import androidx.test.espresso.IdlingRegistry;

import com.hava.trips.utils.DataBindingIdlingResource;
import com.hava.trips.utils.EspressoIdlingResource;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Registers and unregisters IdlingResources with IdlingRegistry at the start and at the end of
 * every test respectively
 */
public class IdlingResourceRule extends TestWatcher {
    public final DataBindingIdlingResource dataBindingIdlingResource = new DataBindingIdlingResource();

    @Override
    protected void starting(Description description) {
        super.starting(description);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.COUNTING_IDLING_RESOURCE);
        IdlingRegistry.getInstance().register(dataBindingIdlingResource);
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.COUNTING_IDLING_RESOURCE);
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource);
    }
}
