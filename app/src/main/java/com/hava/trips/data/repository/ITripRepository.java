package com.hava.trips.data.repository;

import androidx.annotation.NonNull;

import com.hava.trips.data.Source;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.ITripDataSource;

import java.util.List;

import io.reactivex.Observable;

public interface ITripRepository extends ITripDataSource {
    @NonNull
    default Observable<List<Trip>> getObservableTrips(@NonNull FilterParams filter) {
        return getObservableTrips(filter, Source.LOCAL);
    }

    @NonNull
    Observable<List<Trip>> getObservableTrips(@NonNull FilterParams filter, Source source);
}
