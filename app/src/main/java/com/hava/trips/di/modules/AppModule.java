package com.hava.trips.di.modules;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {
    @Provides
    Scheduler provideIOScheduler() {
        return Schedulers.io();
    }
}
