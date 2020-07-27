package com.hava.trips.data.source.local;

import androidx.annotation.NonNull;

import com.hava.trips.data.Source;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.ITripDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TripLocalDataSource implements ITripDataSource {
    private final TripDAO dao;

    @Inject
    public TripLocalDataSource(TripDAO dao) {
        this.dao = dao;
    }

    @Override
    public Observable<Trip> getObservableTrip(@NonNull Long tripId, @NotNull Source source) {
        return dao.getObservableTrip(tripId);
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips() {
        return dao.getObservableTrips();
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips(String query) {
        return query == null || query.isEmpty() ? getObservableTrips() : dao.getObservableTrips(query);
    }

    @NonNull
    @Override
    public Observable<List<Trip>> saveTrips(Trip... trips) {
        dao.saveTrips(trips);
        return Observable.just(Arrays.asList(trips));
    }
}
