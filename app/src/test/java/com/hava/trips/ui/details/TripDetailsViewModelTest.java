package com.hava.trips.ui.details;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hava.trips.TripData;
import com.hava.trips.data.TaskResult;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.repository.TripRepository;
import com.hava.trips.fakes.FakeTripDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static com.hava.trips.LiveDataTestUtils.getOrAwaitValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class TripDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();
    private TripDetailsViewModel viewModel;
    private Scheduler scheduler = Schedulers.trampoline();
    // trip that can be found in both remote and local data source
    private Trip universalTrip;

    @Before
    public void setUp() {
        Trip[] remoteTrips = TripData.remoteTrips().toArray(new Trip[]{});
        universalTrip = remoteTrips[0];
        FakeTripDataSource local = new FakeTripDataSource(universalTrip);
        FakeTripDataSource remote = new FakeTripDataSource(remoteTrips);
        TripRepository repository = new TripRepository(local, remote, scheduler);
        viewModel = new TripDetailsViewModel(repository, scheduler);
    }

    @After
    public void cleanUp() {
        viewModel.onCleared();
    }

    @Test
    public void getShowProgressbar_returnsFalseByDefault() throws InterruptedException {
        boolean showProgressbar = getOrAwaitValue(viewModel.getShowProgressbar());

        assertThat(showProgressbar, equalTo(false));
    }

    @Test
    public void getTripFetchResult_returnsError_whenFetchedTripDoesNotExist() throws InterruptedException {
        viewModel.fetchTrip(Long.MAX_VALUE);

        TaskResult<Trip> tripTaskResult = getOrAwaitValue(viewModel.getTripFetchResult());
        boolean showProgressbar = getOrAwaitValue(viewModel.getShowProgressbar());
        Trip trip = getOrAwaitValue(viewModel.getTrip());

        assertThat(tripTaskResult, instanceOf(TaskResult.Error.class));
        assertThat(showProgressbar, equalTo(false));
        assertThat(trip, nullValue());
    }

    @Test
    public void getTripFetchResult_returnsSuccess_whenFetchedTripExists() throws Exception {
        viewModel.fetchTrip(universalTrip.getId());

        TaskResult<Trip> tripTaskResult = getOrAwaitValue(viewModel.getTripFetchResult());
        boolean showProgressbar = getOrAwaitValue(viewModel.getShowProgressbar());
        Trip trip = getOrAwaitValue(viewModel.getTrip());

        assertThat(tripTaskResult, instanceOf(TaskResult.Success.class));
        assertThat(tripTaskResult.getDataOrFail().getId(), equalTo(universalTrip.getId()));
        assertThat(showProgressbar, equalTo(false));
        assertThat(trip, notNullValue(Trip.class));
        assertThat(trip.getId(), equalTo(universalTrip.getId()));
    }
}