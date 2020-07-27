package com.hava.trips.di.modules.datasource.local;

import com.hava.trips.data.source.local.TripDAO;
import com.hava.trips.data.source.local.TripDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DAOModule {
    @Provides
    @Singleton
    TripDAO provideTripDAO(TripDatabase database) {
        return database.getTripDAO();
    }
}
