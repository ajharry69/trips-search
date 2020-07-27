package com.hava.trips.di;

import com.hava.trips.TripData;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.source.ITripDataSource;
import com.hava.trips.di.qualifiers.LocalTripDataSource;
import com.hava.trips.di.qualifiers.RemoteTripDataSource;
import com.hava.trips.fakes.FakeTripDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class FakeDataSourceModule {
    @Provides
    @Singleton
    @LocalTripDataSource
    ITripDataSource provideTripLocalDataSource() {
        return new FakeTripDataSource();
    }

    @Provides
    @Singleton
    @RemoteTripDataSource
    ITripDataSource provideTripRemoteDataSource() {
        return new FakeTripDataSource(TripData.remoteTrips().toArray(new Trip[]{}));
    }
}
