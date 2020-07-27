package com.hava.trips.data.source.remote;

import androidx.annotation.NonNull;

import com.hava.trips.data.models.Trip;

import java.util.Collections;
import java.util.List;

public class TripRemote {
    private final List<Trip> trips;

    public TripRemote() {
        this(Collections.emptyList());
    }

    public TripRemote(@NonNull List<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    public List<Trip> getTrips() {
        return trips;
    }
}
