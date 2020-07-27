package com.hava.trips.ui.results;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hava.trips.data.models.Trip;

public class TripDiffItemCallback extends DiffUtil.ItemCallback<Trip> {
    @Override
    public boolean areItemsTheSame(@NonNull Trip oldItem, @NonNull Trip newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Trip oldItem, @NonNull Trip newItem) {
        return oldItem.equals(newItem);
    }
}
