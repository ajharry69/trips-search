package com.hava.trips.data.models;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Entity
@Fts4(contentEntity = Trip.class)
public class TripFTS {
    @PrimaryKey
    public Long rowid;
    public String pickupLocation, dropoffLocation, type, driverName, carMake, carModel, carNumber;
}
