package com.hava.trips.data.source.remote;

import androidx.annotation.NonNull;

import com.hava.trips.data.Source;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.ITripDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.hava.trips.data.models.Trip.filtered;

public class TripRemoteDataSource implements ITripDataSource {
    private final List<Trip> tripList = new ArrayList<>();
    private final TripService service;

    @Inject
    public TripRemoteDataSource(TripService service) {
        this.service = service;
    }

    @Override
    public Observable<Trip> getObservableTrip(@NonNull Long tripId, @NotNull Source source) {
        return getObservableTrips().flatMap((Function<List<Trip>, ObservableSource<Trip>>) trips -> {
            for (Trip t : trips) if (t.getId().equals(tripId)) return Observable.just(t);
            return Observable.error(new NullPointerException("Trip with ID " + tripId + " not found"));
        });
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips() {
        return service.getTrips().map(tripRemote -> {
            List<Trip> trips = tripRemote.getTrips();
            tripList.clear();
            tripList.addAll(trips);
            return trips;
        });
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips(String query) {
        if (tripList.isEmpty()) return getObservableTrips()
                .map(trips -> filtered(trips, query));
        return Observable.just(filtered(tripList, query));
    }

    @NonNull
    @Override
    public Observable<List<Trip>> saveTrips(Trip... trips) {
        List<Trip> tripList = Arrays.asList(trips);
        this.tripList.addAll(tripList);
        return Observable.just(tripList);
    }
}
