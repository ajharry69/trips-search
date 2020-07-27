package com.hava.trips.ui.details;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class TripDetailsBindings {
    @BindingAdapter(value = {"imageFromUrl", "placeholder"}, requireAll = false)
    public static void setImageFromUrl(@NonNull ImageView view, String url, Drawable placeholder) {
        if (url == null) {
            view.setImageDrawable(placeholder);
            return;
        }

        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .placeholder(placeholder)
                .into(view);
    }
}
