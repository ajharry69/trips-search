package com.hava.trips.data.source.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TripService {
    @GET("trips/recent.json")
    Observable<TripRemote> getTrips();
}
