package com.hava.trips.ui.details;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hava.trips.data.Source;
import com.hava.trips.data.TaskResult;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.repository.ITripRepository;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class TripDetailsViewModel extends ViewModel {
    private Disposable tripFetchDisposable;
    private final ITripRepository repository;
    private final Scheduler ioScheduler;
    private MutableLiveData<Boolean> showProgressbar = new MutableLiveData<>(false);
    private MutableLiveData<TaskResult<Trip>> tripFetchResult = new MutableLiveData<>();

    @ViewModelInject
    public TripDetailsViewModel(ITripRepository repository, Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    public void fetchTrip(Long tripId) {
        fetchTrip(tripId, Source.LOCAL);
    }

    public void fetchTrip(Long tripId, Source source) {
        showProgressbar.setValue(true);
        tripFetchResult.setValue(new TaskResult.Loading<>());
        repository.getObservableTrip(tripId, source).subscribeOn(ioScheduler).observeOn(ioScheduler)
                .subscribe(new Observer<Trip>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        tripFetchDisposable = d;
                    }

                    @Override
                    public void onNext(Trip trip) {
                        showProgressbar.postValue(false);
                        tripFetchResult.postValue(new TaskResult.Success<>(trip));
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgressbar.postValue(false);
                        tripFetchResult.postValue(new TaskResult.Error<>(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<Boolean> getShowProgressbar() {
        return showProgressbar;
    }

    public LiveData<Trip> getTrip() {
        return Transformations.map(tripFetchResult, input -> {
            Trip trip = null;
            if (input instanceof TaskResult.Success) {
                trip = ((TaskResult.Success<Trip>) input).getData();
            }
            return trip;
        });
    }

    public LiveData<TaskResult<Trip>> getTripFetchResult() {
        return tripFetchResult;
    }

    @Override
    protected void onCleared() {
        if (tripFetchDisposable != null && !tripFetchDisposable.isDisposed())
            tripFetchDisposable.dispose();
        super.onCleared();
    }
}