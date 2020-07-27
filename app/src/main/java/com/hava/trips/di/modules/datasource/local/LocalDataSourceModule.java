package com.hava.trips.di.modules.datasource.local;

import android.content.Context;

import androidx.room.Room;

import com.hava.trips.data.source.local.TripDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Provides Room database for storing cached trip records
 */
@Module
@InstallIn(ApplicationComponent.class)
public class LocalDataSourceModule {
    @Provides
    @Singleton
    TripDatabase providerTripDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), TripDatabase.class, "trips.db")
                .fallbackToDestructiveMigration()
                .build();
    }
}
