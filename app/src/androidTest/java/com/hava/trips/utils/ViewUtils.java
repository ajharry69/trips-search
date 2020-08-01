package com.hava.trips.utils;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class ViewUtils {
    public static ViewAction clickRecyclerViewItemWithId(@IdRes int viewId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "View with id " + viewId + " click action";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.findViewById(viewId).performClick();
//                view.findViewById(viewId).performClick();
            }
        };
    }
}
