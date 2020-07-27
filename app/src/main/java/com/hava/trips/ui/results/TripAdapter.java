package com.hava.trips.ui.results;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hava.trips.data.models.Trip;
import com.hava.trips.databinding.TripItemBinding;

public class TripAdapter extends ListAdapter<Trip, TripAdapter.ViewHolder> {
    protected TripAdapter() {
        super(new TripDiffItemCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TripItemBinding binding = TripItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = getItem(position);
        if (trip != null) holder.bind(trip);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TripItemBinding binding;

        public ViewHolder(@NonNull TripItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull Trip trip) {
            binding.setTrip(trip);
        }
    }
}
