package com.hava.trips.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.hava.trips.R;
import com.hava.trips.data.TaskResult;
import com.hava.trips.databinding.TripDetailsFragmentBinding;
import com.hava.trips.ui.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TripDetailsFragment extends BaseFragment implements OnMapReadyCallback {

    private TripDetailsViewModel mViewModel;
    private TripDetailsFragmentBinding binding;

    @VisibleForTesting
    boolean attachMapCallback = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TripDetailsFragmentBinding.inflate(inflater, container, false);
        setupToolbar(binding.toolbar);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null && attachMapCallback) mapFragment.getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        long tripId = TripDetailsFragmentArgs.fromBundle(requireArguments()).getTripId();
        mViewModel = new ViewModelProvider(this).get(TripDetailsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        mViewModel.fetchTrip(tripId);
        mViewModel.getTripFetchResult().observe(getViewLifecycleOwner(), tripTaskResult -> {
            if (tripTaskResult instanceof TaskResult.Error) {
                Snackbar.make(requireView(), tripTaskResult.getErrorMessage(), Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) return;
        googleMap.setTrafficEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mViewModel.getTrip().observe(getViewLifecycleOwner(), trip -> {
            if (trip == null) return;
            LatLng pickupLocation = new LatLng(trip.getPickupLat(), trip.getPickupLng());
            MarkerOptions pickupLocationMarker = new MarkerOptions()
                    .position(pickupLocation).title(trip.getPickupLocation());

            LatLng dropoffLocation = new LatLng(trip.getDropoffLat(), trip.getDropoffLat());
            MarkerOptions dropoffLocationMarker = new MarkerOptions()
                    .position(dropoffLocation).title(trip.getDropoffLocation());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickupLocation, 18f));
            googleMap.addMarker(pickupLocationMarker);
            googleMap.addMarker(dropoffLocationMarker);
        });
    }
}