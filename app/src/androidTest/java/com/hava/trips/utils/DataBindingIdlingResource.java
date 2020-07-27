package com.hava.trips.utils;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DataBindingIdlingResource implements IdlingResource {
    // Give it a unique id to work around an Espresso bug where you cannot register/unregister
    // an idling resource with the same name.
    private static final String id = UUID.randomUUID().toString();
    // List of registered callbacks
    private static final List<IdlingResource.ResourceCallback> idlingCallbacks = new ArrayList<>();
    // Holds whether isIdle was called and the result was false. We track this to avoid calling
    // onTransitionToIdle callbacks if Espresso never thought we were idle in the first place.
    private boolean wasNotIdle = false;
    private FragmentActivity activity;

    @Override
    public String getName() {
        return String.format("DataBinding %s", id);
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = false;
        for (ViewDataBinding b : getBindings()) {
            if (!b.hasPendingBindings()) {
                idle = true;
                break;
            }
        }
        if (idle) {
            if (wasNotIdle) {
                // Notify observers to avoid Espresso race detector.
                for (ResourceCallback cb : idlingCallbacks) cb.onTransitionToIdle();
            }
            wasNotIdle = false;
        } else {
            wasNotIdle = true;
            // Check next frame.
            if (activity != null) {
                activity.findViewById(android.R.id.content)
                        .postDelayed(this::isIdleNow, 16);
            }
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        idlingCallbacks.add(callback);
    }

    /**
     * Sets the activity from an [ActivityScenario] to be used from [DataBindingIdlingResource].
     */
    public <T extends FragmentActivity> void monitorActivity(ActivityScenario<T> activityScenario) {
        activityScenario.onActivity(fragmentActivity -> activity = fragmentActivity);
    }

    /**
     * Sets the fragment from a [FragmentScenario] to be used from [DataBindingIdlingResource].
     */
    public <T extends Fragment> void monitorFragment(FragmentScenario<T> fragmentScenario) {
        fragmentScenario.onFragment(fragment -> activity = fragment.requireActivity());
    }

    @Nullable
    private ViewDataBinding getBinding(View view) {
        return DataBindingUtil.getBinding(view);
    }

    private List<ViewDataBinding> getBindings() {
        List<Fragment> fragments = activity == null ? Collections.emptyList() :
                activity.getSupportFragmentManager().getFragments();

        List<ViewDataBinding> bindings = new ArrayList<>();
        for (Fragment f : fragments) {
            if (f.getView() == null) continue;
            bindings.add(getBinding(f.getView()));
            for (Fragment cf : f.getChildFragmentManager().getFragments()) {
                if (cf.getView() == null) continue;
                bindings.add(getBinding(cf.getView()));
            }
        }
        return bindings;
    }
}
