package com.hava.trips.di.modules.datasource.remote;

import com.hava.trips.data.source.remote.TripService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ApplicationComponent.class)
public class ServiceModule {
    @Provides
    @Singleton
    TripService provideTripService(Retrofit retrofit) {
        return retrofit.create(TripService.class);
    }
}
