package com.hava.trips.fakes;

import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.local.TripDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static com.hava.trips.data.models.Trip.filtered;

public class FakeTripDao implements TripDAO {
    private final List<Trip> tripsDb = new ArrayList<>();

    public FakeTripDao(Trip... trips) {
        saveTrips(trips);
    }

    @Override
    public Long[] saveTrips(Trip... trips) {
        tripsDb.addAll(Arrays.asList(trips));
        return new Long[trips.length];
    }

    @Override
    public Observable<Trip> getObservableTrip(Long tripId) {
        for (Trip t : tripsDb) if (t.getId().equals(tripId)) return Observable.just(t);
        return Observable.error(new NullPointerException("Trip with ID " + tripId + " not found"));
    }

    @Override
    public Observable<List<Trip>> getObservableTrips() {
        return Observable.just(Arrays.asList(tripsDb.toArray(new Trip[]{})));
    }

    @Override
    public Observable<List<Trip>> getObservableTrips(String query) {
        if (tripsDb.isEmpty()) return getObservableTrips();
        return Observable.just(filtered(tripsDb, query));
    }
}
