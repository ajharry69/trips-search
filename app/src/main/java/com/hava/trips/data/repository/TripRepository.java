package com.hava.trips.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.hava.trips.R;
import com.hava.trips.data.Source;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.ITripDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

public class TripRepository implements ITripRepository {
    private final ITripDataSource local, remote;
    private final Scheduler ioScheduler;

    @Inject
    public TripRepository(ITripDataSource local, ITripDataSource remote, Scheduler ioScheduler) {
        this.local = local;
        this.remote = remote;
        this.ioScheduler = ioScheduler;
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips(@NonNull FilterParams filter, Source source) {
        Observable<List<Trip>> listObservable;
        switch (source) {
            case REMOTE:
                listObservable = remote.getObservableTrips(filter.getKeyword());
                break;
            case LOCAL:
                listObservable = local.getObservableTrips(filter.getKeyword());
                break;
            default:
                listObservable = getObservableTrips(filter, Source.LOCAL);
        }
        return listObservable.subscribeOn(ioScheduler).map(trips -> {
            Set<Trip> _trips = filterTrips(trips, filter);
            return Arrays.asList(_trips.toArray(new Trip[]{}));
        });
    }

    @Override
    public Observable<Trip> getObservableTrip(@NonNull Long tripId, @NotNull Source source) {
        Observable<Trip> tripObservable;
        switch (source) {
            case REMOTE:
                tripObservable = remote.getObservableTrip(tripId, source);
                break;
            case LOCAL:
                tripObservable = local.getObservableTrip(tripId, source);
                break;
            default:
                tripObservable = getObservableTrip(tripId, Source.LOCAL);
        }
        return tripObservable;
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips() {
        return remote.getObservableTrips().subscribeOn(ioScheduler)
                .flatMap((Function<List<Trip>, ObservableSource<List<Trip>>>) trips ->
                        local.saveTrips(trips.toArray(new Trip[]{})));
    }

    @NonNull
    @Override
    public Observable<List<Trip>> getObservableTrips(String query) {
        return remote.getObservableTrips(query).subscribeOn(ioScheduler)
                .flatMap((Function<List<Trip>, ObservableSource<List<Trip>>>) trips ->
                        local.saveTrips(trips.toArray(new Trip[]{})));
    }

    @NonNull
    @Override
    public Observable<List<Trip>> saveTrips(Trip... trips) {
        return remote.saveTrips(trips).subscribeOn(ioScheduler)
                .flatMap((Function<List<Trip>, ObservableSource<List<Trip>>>) trips1 ->
                        local.saveTrips(trips1.toArray(new Trip[]{})));
    }

    @VisibleForTesting
    Set<Trip> filterByDistance(Trip trip, @NonNull FilterParams filter, Set<Trip> _trips) {
        switch (filter.getDistance()) {
            case R.id.distance_under_3:
                if (trip.getDistance() < 3) _trips.add(trip);
                else _trips.remove(trip);
                break;
            case R.id.distance_3_to_8:
                if (trip.getDistance() >= 3 && trip.getDistance() <= 8) _trips.add(trip);
                else _trips.remove(trip);
                break;
            case R.id.distance_8_to_15:
                if (trip.getDistance() > 8 && trip.getDistance() <= 15) _trips.add(trip);
                else _trips.remove(trip);
                break;
            case R.id.distance_more_than_15:
                if (trip.getDistance() > 15) _trips.add(trip);
                else _trips.remove(trip);
                break;
            default:
                // any distance will be included
                break;
        }
        return _trips;
    }

    @VisibleForTesting
    Set<Trip> filterByTime(Trip trip, @NonNull FilterParams filter, Set<Trip> _trips) {
        switch (filter.getTime()) {
            case R.id.time_under_5:
                if (trip.getDuration() < 5) {
                    _trips.add(trip);
                } else _trips.remove(trip);
                break;
            case R.id.time_5_to_10:
                if (trip.getDuration() >= 5 && trip.getDuration() <= 10) {
                    _trips.add(trip);
                } else _trips.remove(trip);
                break;
            case R.id.time_10_to_20:
                if (trip.getDuration() > 10 && trip.getDuration() <= 20) {
                    _trips.add(trip);
                } else _trips.remove(trip);
                break;
            case R.id.time_more_than_20:
                if (trip.getDuration() > 20) {
                    _trips.add(trip);
                } else _trips.remove(trip);
                break;
            default:
                // any time will be included
                break;
        }
        return _trips;
    }

    @VisibleForTesting
    Set<Trip> filterByStatus(Trip trip, @NonNull FilterParams filter, Set<Trip> _trips) {
        if (!filter.isIncludeCancelled() && trip.getStatus() == Trip.Status.CANCELLED) {
            _trips.remove(trip);
        }
        return _trips;
    }

    @NotNull
    @VisibleForTesting
    Set<Trip> filterTrips(List<Trip> trips, @NonNull FilterParams filter) {
        Set<Trip> _trips = new HashSet<>(trips);
        for (Trip trip : trips) {
            filterByDistance(trip, filter, _trips);

            filterByTime(trip, filter, _trips);

            filterByStatus(trip, filter, _trips);
        }
        return _trips;
    }
}
