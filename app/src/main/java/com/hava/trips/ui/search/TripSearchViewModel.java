package com.hava.trips.ui.search;

import androidx.annotation.NonNull;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.hava.trips.data.models.FilterParams;

import static com.hava.trips.utils.Constants.FILTER_PARAMS_SAVED_STATE_KEY;

public class TripSearchViewModel extends ViewModel {
    private final SavedStateHandle savedStateHandle;
    private MutableLiveData<Boolean> isFilterParamsSaved = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isNavigateToResultsPage = new MutableLiveData<>(false);

    @ViewModelInject
    public TripSearchViewModel(@Assisted SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        // Used as default filter param
        savedStateHandle.set(FILTER_PARAMS_SAVED_STATE_KEY, FilterParams.DEFAULT);
    }

    public void saveFilterParams(@NonNull FilterParams params) {
        savedStateHandle.set(FILTER_PARAMS_SAVED_STATE_KEY, params);
        isFilterParamsSaved.setValue(savedStateHandle.contains(FILTER_PARAMS_SAVED_STATE_KEY));
        setIsNavigateToResultsPage(true);
    }

    public void setIsNavigateToResultsPage(boolean navigateToResultsPage) {
        isNavigateToResultsPage.setValue(navigateToResultsPage);
    }

    public LiveData<Boolean> isFilterParamsSaved() {
        return isFilterParamsSaved;
    }

    public LiveData<Boolean> isNavigateToResultsPage() {
        return isNavigateToResultsPage;
    }

    public LiveData<FilterParams> getObservableFilterParams() {
        return savedStateHandle.getLiveData(FILTER_PARAMS_SAVED_STATE_KEY);
    }
}