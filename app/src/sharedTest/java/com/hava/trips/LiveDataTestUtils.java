package com.hava.trips;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtils {
    public static <T> T getOrAwaitValue(@NonNull final LiveData<T> liveData) throws InterruptedException {
        return getOrAwaitValue(liveData, 2L);
    }

    public static <T> T getOrAwaitValue(@NonNull final LiveData<T> liveData, Long timeOut) throws InterruptedException {
        return getOrAwaitValue(liveData, timeOut, TimeUnit.SECONDS);
    }

    public static <T> T getOrAwaitValue(@NonNull final LiveData<T> liveData, Long timeOut, TimeUnit timeUnit) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(timeOut, timeUnit)) {
            throw new RuntimeException("LiveData value was never set.");
        }
        //noinspection unchecked
        return (T) data[0];
    }
}
