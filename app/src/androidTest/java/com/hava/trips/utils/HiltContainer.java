package com.hava.trips.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.hava.trips.R;

import static java.util.Objects.requireNonNull;

public class HiltContainer {
    public static <A extends FragmentActivity, F extends Fragment> void launchFragmentInHiltContainer(
            @NonNull Class<A> activityClass, @NonNull Class<F> fragmentClass) {
        launchFragmentInHiltContainer(activityClass, fragmentClass, null);
    }

    public static <A extends FragmentActivity, F extends Fragment> void launchFragmentInHiltContainer(
            @NonNull Class<A> activityClass, @NonNull Class<F> fragmentClass, @Nullable Bundle fragmentArgs) {
        launchFragmentInHiltContainer(activityClass, fragmentClass, fragmentArgs, R.style.Theme_HavaTrips);
    }

    public static <A extends FragmentActivity, F extends Fragment> void launchFragmentInHiltContainer(
            @NonNull Class<A> activityClass,
            @NonNull Class<F> fragmentClass,
            @Nullable Bundle fragmentArgs,
            @StyleRes int themeResId) {
        launchFragmentInHiltContainer(activityClass, fragmentClass, fragmentArgs, themeResId, null);
    }

    public static <A extends FragmentActivity, F extends Fragment> void launchFragmentInHiltContainer(
            @NonNull Class<A> activityClass,
            @NonNull Class<F> fragmentClass,
            @Nullable Bundle fragmentArgs,
            @StringRes int themeResId,
            Function<F> action) {
        ComponentName componentName = new ComponentName(ApplicationProvider.getApplicationContext(), activityClass);
        Intent startActivityIntent = Intent.makeMainActivity(componentName)
                .putExtra(EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId);

        ActivityScenario<A> activityScenario = ActivityScenario.launch(startActivityIntent);
        activityScenario.onActivity(activity -> {
            @SuppressWarnings("unchecked")
            F fragment = (F) activity.getSupportFragmentManager().getFragmentFactory()
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
