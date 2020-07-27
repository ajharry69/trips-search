package com.hava.trips.fakes;

import com.hava.trips.data.source.remote.TripRemote;
import com.hava.trips.data.source.remote.TripService;

import io.reactivex.Observable;

public class FakeTripService implements TripService {
    private TripRemote tripRemote;

    @Override
    public Observable<TripRemote> getTrips() {
        if (getTripRemote() == null)
            return Observable.error(new NullPointerException("trip remote instance was not set"));
        return Observable.just(getTripRemote());
    }

    public TripRemote getTripRemote() {
        return tripRemote;
    }

    public void setTripRemote(TripRemote tripRemote) {
        this.tripRemote = tripRemote;
    }
}
