package com.hava.trips.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hava.trips.data.models.Trip;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface TripDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] saveTrips(Trip... trips);

    /*@Query("INSERT INTO tripfts(tripfts) VALUES('rebuild')")
    void rebuild();*/

    @Query("SELECT * FROM trips WHERE id = :tripId")
    Observable<Trip> getObservableTrip(Long tripId);

    @Query("SELECT * FROM trips")
    Observable<List<Trip>> getObservableTrips();

    @Query("SELECT t.* FROM tripfts INNER JOIN trips AS t ON (tripfts.rowid = t.id) WHERE tripfts MATCH :query")
    Observable<List<Trip>> getObservableTrips(String query);
}
