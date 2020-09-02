package com.hava.trips.ui.results;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.arch.core.util.Function;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hava.trips.R;
import com.hava.trips.data.Source;
import com.hava.trips.data.TaskResult;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.repository.ITripRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import static com.hava.trips.utils.Constants.FILTER_PARAMS_SAVED_STATE_KEY;

public class TripResultsViewModel extends ViewModel {
    private Disposable remoteTripsFetchDisposable, filteredTripsFetchDisposable;
    private final ITripRepository repository;
    private final Scheduler ioScheduler;

    @VisibleForTesting
    boolean isFiltrationInProgress = false;
    private MutableLiveData<Integer> filteredResultsCount = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> showProgressbar = new MutableLiveData<>(false);
    private MutableLiveData<Integer> loadingStatusMessage = new MutableLiveData<>(R.string.status_searching_trips);
    private MutableLiveData<TaskResult<List<Trip>>> remoteTripsFetchResult = new MutableLiveData<>();
    private MutableLiveData<List<Trip>> filteredTrips = new MutableLiveData<>();
    private LiveData<TaskResult<List<Trip>>> _taskResultLiveData;
    private androidx.lifecycle.Observer<TaskResult<List<Trip>>> _taskResultObserver = listTaskResult -> {

    };

    @ViewModelInject
    public TripResultsViewModel(ITripRepository repository, @Assisted SavedStateHandle savedStateHandle, Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
        FilterParams savedFilterParams = savedStateHandle.get(FILTER_PARAMS_SAVED_STATE_KEY);
        if (savedFilterParams != null) {
            _taskResultLiveData = setFilterParams(savedFilterParams);
            // enables trip filtration to be initiated after remote trips have been fetched
            _taskResultLiveData.observeForever(_taskResultObserver);
        }
    }

    public LiveData<TaskResult<List<Trip>>> setFilterParams(@NonNull FilterParams filterParams) {
        return setFilterParams(filterParams, Source.LOCAL);
    }

    public LiveData<TaskResult<List<Trip>>> setFilterParams(@NonNull FilterParams filterParams, Source source) {
        // filtration can either be initiated from saved filter params retrieved from
        // SavedStateHandle or filter params passed thorough this methods parameter hence the need
        // to temporarily suspend subsequent filtration initiation if filtration is already in
        // progress
        if (isFiltrationInProgress) return remoteTripsFetchResult;
        getTrips(); // initiates trip list fetch from remote data source...
        return Transformations.map(remoteTripsFetchResult, (Function<TaskResult<List<Trip>>, TaskResult<List<Trip>>>) input -> {
            // Remote data fetch complete. Start filtration...
            isFiltrationInProgress = true;
            loadingStatusMessage.setValue(R.string.status_searching_trips);
            filteredTripsFetchDisposable = repository.getObservableTrips(filterParams, source)
                    .subscribeOn(ioScheduler).observeOn(ioScheduler)
                    .subscribe(trips -> {
                        int tripsCount = trips.size();
                        filteredResultsCount.postValue(tripsCount);
                        showProgressbar.postValue(tripsCount <= 0);
                        filteredTrips.postValue(trips);
                        isFiltrationInProgress = false;
                    });
            return input;
        });
    }

    /**
     * Fetch trips from remote data source
     */
    @VisibleForTesting
    void getTrips() {
        setShowProgressbar(true);
        remoteTripsFetchResult.setValue(new TaskResult.Loading<>());
        loadingStatusMessage.setValue(R.string.status_fetching_remote_trips);
        // TODO: Test status before...
        repository.getObservableTrips().subscribeOn(ioScheduler).observeOn(ioScheduler)
                .subscribe(new Observer<List<Trip>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        remoteTripsFetchDisposable = d;
                    }

                    @Override
                    public void onNext(List<Trip> trips) {
                        setShowProgressbar(false);
                        remoteTripsFetchResult.postValue(new TaskResult.Success<>(trips));
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingStatusMessage.postValue(R.string.status_trips_empty);
                        setShowProgressbar(false);
                        remoteTripsFetchResult.postValue(new TaskResult.Error<>(e));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public LiveData<Integer> getFilteredResultsCount() {
        return filteredResultsCount;
    }

    public void setShowProgressbar(boolean show) {
        this.showProgressbar.setValue(show);
    }

    public LiveData<Boolean> getShowProgressbar() {
        return showProgressbar;
    }

    public LiveData<List<Trip>> getFilteredTrips() {
        return filteredTrips;
    }

    public LiveData<TaskResult<List<Trip>>> getRemoteTripsFetchResult() {
        return remoteTripsFetchResult;
    }

    public LiveData<Integer> getLoadingStatusMessage() {
        return loadingStatusMessage;
    }

    @Override
    protected void onCleared() {
        if (remoteTripsFetchDisposable != null && !remoteTripsFetchDisposable.isDisposed())
            remoteTripsFetchDisposable.dispose();
        if (filteredTripsFetchDisposable != null && !filteredTripsFetchDisposable.isDisposed())
            filteredTripsFetchDisposable.dispose();
        if (_taskResultLiveData != null) _taskResultLiveData.removeObserver(_taskResultObserver);
        super.onCleared();
    }
}