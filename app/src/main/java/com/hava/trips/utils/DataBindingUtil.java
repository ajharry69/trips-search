package com.hava.trips.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class DataBindingUtil {
    @BindingAdapter(value = {"isVisible"})
    public static void setVisible(View view, boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }
}
