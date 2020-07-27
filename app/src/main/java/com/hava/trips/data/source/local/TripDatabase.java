package com.hava.trips.data.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.hava.trips.data.models.Trip;
import com.hava.trips.data.models.TripFTS;

@Database(entities = {Trip.class, TripFTS.class}, version = 1)
@TypeConverters(StatusConverter.class)
public abstract class TripDatabase extends RoomDatabase {
    public abstract TripDAO getTripDAO();
}
