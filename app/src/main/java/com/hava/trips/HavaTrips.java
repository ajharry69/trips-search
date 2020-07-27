package com.hava.trips;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class HavaTrips extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
