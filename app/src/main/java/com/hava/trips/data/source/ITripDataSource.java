package com.hava.trips.data.source;

import androidx.annotation.NonNull;

import com.hava.trips.data.Source;
import com.hava.trips.data.models.Trip;

import java.util.List;

import io.reactivex.Observable;


public interface ITripDataSource {
    Observable<Trip> getObservableTrip(@NonNull Long tripId, @NonNull Source source);

    @NonNull
    default Observable<List<Trip>> getObservableTrips() {
        return getObservableTrips(null);
    }

    @NonNull
    Observable<List<Trip>> getObservableTrips(String query);

    /**
     * @param trips trips to be saved
     * @return an Observable of saved trips(passed as parameters)
     */
    @NonNull
    Observable<List<Trip>> saveTrips(Trip... trips);
}
