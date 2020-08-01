package com.hava.trips;

import androidx.annotation.NonNull;

import com.hava.trips.data.models.Trip;

import java.util.ArrayList;
import java.util.List;

import static com.hava.trips.data.models.Trip.Status.CANCELLED;
import static com.hava.trips.data.models.Trip.Status.COMPLETED;

public class TripData {
    public static Trip TRIP = new Trip.Builder(1L)
            .setStatus(COMPLETED)
            .setRequestDate("2019-08-16 10:49:25")
            .setPickupLat(-1.3234923)
            .setPickupLng(36.8435638)
            .setPickupLocation("St James, Nairobi")
            .setDropoffLat(-1.323413)
            .setDropoffLng(36.8434199)
            .setDropoffLocation("Nextgen Mall, Nairobi")
            .setPickupDate("2019-08-16 10:50:32")
            .setDropoffDate("2019-08-16 11:26:32")
            .setType("Lady")
            .setDriverId(1)
            .setDriverName("John Doe")
            .setDriverRating(5)
            .setDriverPic(null)
            .setCarMake("Honda")
            .setCarModel("Civic")
            .setCarNumber("KCR-100P")
            .setCarYear(2012)
            .setCarPic(null)
            .setDuration(36)
            .setDurationUnit("min")
            .setDistance(1.45f)
            .setDistanceUnit("km")
            .setCost(253)
            .setCostUnit("KES")
            .build();

    public static final Trip FILTERABLE_TRIP = TRIP;

    @NonNull
    public static Trip createTrip(Long id) {
        return builder(id).build();
    }

    public static Trip.Builder builder(Long id) {
        return new Trip.Builder(id);
    }

    public static List<Trip> remoteTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(TRIP);
        trips.add(new Trip.Builder(2L)
                .setStatus(COMPLETED)
                .setRequestDate("2019-08-09 06:45:05")
                .setPickupLat(-1.2952686)
                .setPickupLng(36.8866489)
                .setPickupLocation("Manyanja, Nairobi")
                .setDropoffLat(-1.3234763)
                .setDropoffLng(36.843356)
                .setDropoffLocation("St James, Nairobi")
                .setPickupDate("2019-08-09 06:45:38")
                .setDropoffDate("2019-08-09 08:34:38")
                .setType("Basic")
                .setDriverId(2)
                .setDriverName("Richard")
                .setDriverRating(0)
                .setDriverPic(null)
                .setCarMake("Nissan")
                .setCarModel("March")
                .setCarNumber("KCQ-6711")
                .setCarYear(2015)
                .setCarPic(null)
                .setDuration(109)
                .setDurationUnit("min")
                .setDistance(15.51f)
                .setDistanceUnit("km")
                .setCost(894)
                .setCostUnit("KES")
                .build());
        trips.add(new Trip.Builder(3L)
                .setStatus(CANCELLED)
                .setRequestDate("2019-08-07 16:35:06")
                .setPickupLat(-1.32593)
                .setPickupLng(36.8402983)
                .setPickupLocation("Bandari, Nairobi")
                .setDropoffLat(-1.3221375)
                .setDropoffLng(36.8288983)
                .setDropoffLocation("New Apostolic Church, Nairobi")
                .setPickupDate("2019-08-07 16:35:39")
                .setDropoffDate(null)
                .setType("HavaXL")
                .setDriverId(3)
                .setDriverName("George")
                .setDriverRating(0)
                .setDriverPic(null)
                .setCarMake("Toyota")
                .setCarModel("Rav4")
                .setCarNumber("KBG-871X")
                .setCarYear(2009)
                .setCarPic("")
                .setDuration(0)
                .setDurationUnit("min")
                .setDistance(0)
                .setDistanceUnit("km")
                .setCost(0)
                .setCostUnit("KES")
                .build());
        trips.add(new Trip.Builder(4L)
                .setStatus(CANCELLED)
                .setRequestDate("2019-07-23 12:53:53")
                .setPickupLat(-1.2928247)
                .setPickupLng(36.8198777)
                .setPickupLocation("Ring Road Kileleshwa, Nairobi")
                .setDropoffLat(-1.3240944)
                .setDropoffLng(36.8442972)
                .setDropoffLocation("Next Gen Mall, Nairobi")
                .setPickupDate("2019-07-23 12:54:17")
                .setDropoffDate(null)
                .setType("Lady")
                .setDriverId(1)
                .setDriverName("Alize")
                .setDriverRating(0)
                .setCarMake("Honda")
                .setCarModel("Civic")
                .setCarNumber("KCR-100P")
                .setCarYear(2012)
                .setDuration(0)
                .setDurationUnit("")
                .setDistance(0)
                .setDistanceUnit("")
                .setCost(0)
                .setCostUnit("")
                .build());

        return trips;
    }
}