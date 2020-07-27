package com.hava.trips.data.source.local;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import com.hava.trips.data.models.Trip.Status;

public class StatusConverter {
    @TypeConverter
    public String fromStatusToString(Status status) {
        return (status == null ? Status.CANCELLED : status).name();
    }

    @NonNull
    @TypeConverter
    public Status fromStringToStatus(String status) {
        return Status.valueOf(status);
    }
}
