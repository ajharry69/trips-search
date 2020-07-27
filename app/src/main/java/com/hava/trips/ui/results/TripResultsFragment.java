package com.hava.trips.ui.results;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.hava.trips.data.TaskResult;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.data.models.Trip;
import com.hava.trips.databinding.TripResultsFragmentBinding;
import com.hava.trips.ui.BaseFragment;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TripResultsFragment extends BaseFragment {

    private TripResultsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TripResultsFragmentBinding.inflate(inflater, container, false);
        setupToolbar(binding.toolbar);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FilterParams filterParams = TripResultsFragmentArgs.fromBundle(requireArguments()).getFilterParams();
        TripResultsViewModel mViewModel = new ViewModelProvider(this).get(TripResultsViewModel.class);
        // initiates trip list remote fetching and filtering afterwards...
        mViewModel.setFilterParams(filterParams).observe(getViewLifecycleOwner(), listTaskResult -> {

        });

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(mViewModel);

        TripAdapter adapter = new TripAdapter();
        binding.trips.setAdapter(adapter);

        mViewModel.getFilteredTrips().observe(getViewLifecycleOwner(), trips -> {
            if (trips == null) return;
            adapter.submitList(trips);
        });
        mViewModel.getRemoteTripsFetchResult().observe(getViewLifecycleOwner(), taskResult -> {
            if (taskResult instanceof TaskResult.Error) {
                // TODO: Update UI properly...
                String errorMessage = taskResult.getErrorMessage();
                Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show();
                Log.e(TAG, errorMessage, ((TaskResult.Error<List<Trip>>) taskResult).getError());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}