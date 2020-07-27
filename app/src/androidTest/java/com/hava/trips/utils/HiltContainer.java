package com.hava.trips.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.hava.trips.HiltTestActivity;
import com.hava.trips.R;

import static java.util.Objects.requireNonNull;

public class HiltContainer {
    public static <T extends Fragment> void launchFragmentInHiltContainer(@NonNull Class<T> fragmentClass) {
        launchFragmentInHiltContainer(fragmentClass, null);
    }

    public static <T extends Fragment> void launchFragmentInHiltContainer(@NonNull Class<T> fragmentClass, @Nullable Bundle fragmentArgs) {
        launchFragmentInHiltContainer(fragmentClass, fragmentArgs, R.style.Theme_HavaTrips);
    }

    public static <T extends Fragment> void launchFragmentInHiltContainer(@NonNull Class<T> fragmentClass, @Nullable Bundle fragmentArgs, @StyleRes int themeResId) {
        launchFragmentInHiltContainer(fragmentClass, fragmentArgs, themeResId, null);
    }

    public static <T extends Fragment> void launchFragmentInHiltContainer(@NonNull Class<T> fragmentClass, @Nullable Bundle fragmentArgs, @StringRes int themeResId, Function<T> action) {
        ComponentName componentName = new ComponentName(ApplicationProvider.getApplicationContext(), HiltTestActivity.class);
        Intent startActivityIntent = Intent.makeMainActivity(componentName)
                .putExtra(EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId);

        ActivityScenario<HiltTestActivity> activityScenario = ActivityScenario.launch(startActivityIntent);
        activityScenario.onActivity(activity -> {
            @SuppressWarnings("unchecked")
            T fragment = (T) activity.getSupportFragmentManager().getFragmentFactory()
                    .instantiate(requireNonNull(fragmentClass.getClassLoader()), fragmentClass.getName());
            fragment.setArguments(fragmentArgs);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, "")
                    .commitNow();
            action.apply(fragment);
        });
    }
}
