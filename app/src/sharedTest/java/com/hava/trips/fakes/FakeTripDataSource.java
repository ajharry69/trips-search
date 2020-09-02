package com.hava.trips.fakes;

import androidx.annotation.NonNull;

import com.hava.trips.data.Source;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.ITripDataSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

import static com.hava.trips.data.models.Trip.filtered;

public class FakeTripDataSource implements ITripDataSource {

    private final Set<Trip> trips = new HashSet<>();

    public FakeTripDataSource(Trip... trips) {
        this.trips.addAll(Arrays.asList(trips));
    }

    public int getTripsCount() {
        return trips.size();
    }

    @Override
    public Observable<Trip> getObservableTrip(@NonNull Long tripId, @NonNull Source source) {
        for (Trip t : trips) if (t.getId().equals(tripId)) return Observable.just(t);
        return Observable.error(new NullPointerException("Trip with ID " + tripId + " not found"));
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips(String query) {
        List<Trip> trips = Arrays.asList(this.trips.toArray(new Trip[]{}));
        return Observable.just(trips.isEmpty() ? trips : filtered(trips, query));
    }

    @NonNull
    @Override
    public Observable<List<Trip>> saveTrips(Trip... trips) {
        this.trips.addAll(Arrays.asList(trips));
        return Observable.just(Arrays.asList(trips));
    }
}
