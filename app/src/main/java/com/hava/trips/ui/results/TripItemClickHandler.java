package com.hava.trips.ui.results;

import android.view.View;

import androidx.navigation.Navigation;

import static com.hava.trips.ui.results.TripResultsFragmentDirections.actionDestTripResultsToDestTripDetails;

public class TripItemClickHandler {
    public static void onOptionsViewClicked(View button, Long tripId) {
        Navigation.findNavController(button).navigate(actionDestTripResultsToDestTripDetails(tripId));
    }
}
