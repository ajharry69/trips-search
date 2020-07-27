package com.hava.trips.di.modules;

import com.hava.trips.data.repository.ITripRepository;
import com.hava.trips.data.repository.TripRepository;
import com.hava.trips.data.source.ITripDataSource;
import com.hava.trips.di.qualifiers.LocalTripDataSource;
import com.hava.trips.di.qualifiers.RemoteTripDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import io.reactivex.Scheduler;

@Module
@InstallIn(ApplicationComponent.class)
public class RepositoryModule {
    @Provides
    @Singleton
    ITripRepository provideTripRepository(
            @LocalTripDataSource ITripDataSource local,
            @RemoteTripDataSource ITripDataSource remote,
            Scheduler scheduler
    ) {
        return new TripRepository(local, remote, scheduler);
    }
}
