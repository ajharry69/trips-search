package com.hava.trips.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hava.trips.data.models.FilterParams;
import com.hava.trips.databinding.TripSearchFragmentBinding;
import com.hava.trips.ui.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

import static androidx.navigation.Navigation.findNavController;
import static com.hava.trips.ui.search.TripSearchFragmentDirections.actionDestTripSearchToDestTripResults;

@AndroidEntryPoint
public class TripSearchFragment extends BaseFragment {
    private FilterParams filterParams = new FilterParams.Builder().build();
    private TripSearchViewModel mViewModel;
    private TripSearchFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TripSearchFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.includeCancelledLabel.setOnClickListener(view12 -> binding.includeCancelled.performClick());
        binding.search.setOnClickListener(view1 -> {
            filterParams = getQueryFromInputs();
            if (filterParams == null) return;
            mViewModel.saveFilterParams(filterParams);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TripSearchViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        mViewModel.isNavigateToResultsPage().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                findNavController(binding.search)
                        .navigate(actionDestTripSearchToDestTripResults(filterParams));
                mViewModel.setIsNavigateToResultsPage(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Nullable
    private FilterParams getQueryFromInputs() {
        Editable editableKeyword = binding.keyword.getText();
        FilterParams.Builder builder = new FilterParams.Builder()
                .setKeyword(editableKeyword == null ? "" : editableKeyword.toString())
                .setDistance(binding.distanceOptions.getCheckedRadioButtonId())
                .setTime(binding.timeOptions.getCheckedRadioButtonId())
                .setIncludeCancelled(binding.includeCancelled.isChecked());
        return builder.build();
    }
}