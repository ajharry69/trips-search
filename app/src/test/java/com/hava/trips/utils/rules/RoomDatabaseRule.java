package com.hava.trips.utils.rules;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.hava.trips.data.source.local.TripDatabase;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class RoomDatabaseRule extends TestWatcher {
    public TripDatabase database;

    @Override
    protected void starting(Description description) {
        super.starting(description);
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TripDatabase.class
        ).allowMainThreadQueries().build();
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        database.clearAllTables();
        database.close();
    }
}
