package com.hava.trips.utils;

import androidx.test.espresso.idling.CountingIdlingResource;

/**
 * Will be used in actual code to track whether long running tasks in the application
 * are still working. It's made singleton to make it usable from anywhere in the code
 */
public class EspressoIdlingResource {
    private static final String GLOBAL = "global";

    /**
     * Enables incrementing and decrementing a counter. i.e when counter > 0 the app
     * is considered working and when the counter is less than 0, the app is considered
     * idle
     */
    public static final CountingIdlingResource COUNTING_IDLING_RESOURCE = new CountingIdlingResource(GLOBAL);

    public static void decrement() {
        if (!COUNTING_IDLING_RESOURCE.isIdleNow()) COUNTING_IDLING_RESOURCE.decrement();
    }

    public static void increment() {
        COUNTING_IDLING_RESOURCE.increment();
    }

    public static <T> T wrapEspressoIdlingResource(FunctionExecutor<T> function) {
        EspressoIdlingResource.increment(); // the app is busy
        try {
            return function.invoke();
        } finally {
            EspressoIdlingResource.decrement(); // the app is idle
        }
    }
}
