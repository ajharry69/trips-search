package com.hava.trips.di.modules;

import com.hava.trips.data.source.ITripDataSource;
import com.hava.trips.data.source.local.TripDAO;
import com.hava.trips.data.source.local.TripLocalDataSource;
import com.hava.trips.data.source.remote.TripRemoteDataSource;
import com.hava.trips.data.source.remote.TripService;
import com.hava.trips.di.qualifiers.LocalTripDataSource;
import com.hava.trips.di.qualifiers.RemoteTripDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataSourceModule {
    @Provides
    @Singleton
    @LocalTripDataSource
    ITripDataSource provideTripLocalDataSource(TripDAO dao) {
        return new TripLocalDataSource(dao);
    }

    @Provides
    @Singleton
    @RemoteTripDataSource
    ITripDataSource provideTripRemoteDataSource(TripService service) {
        return new TripRemoteDataSource(service);
    }
}
