package com.hava.trips.ui.results;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.hava.trips.TripData;
import com.hava.trips.data.TaskResult;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.repository.TripRepository;
import com.hava.trips.fakes.FakeTripDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static com.hava.trips.LiveDataTestUtils.getOrAwaitValue;
import static com.hava.trips.TripData.FILTERABLE_TRIP;
import static com.hava.trips.utils.Constants.FILTER_PARAMS_SAVED_STATE_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;

public class TripResultsViewModelTest {

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();
    private TripResultsViewModel viewModel;
    private Scheduler scheduler = Schedulers.trampoline();
    private static final String FILTER_KEYWORD = FILTERABLE_TRIP.getDriverName();

    @Before
    public void setUp() {
        Trip[] trips = TripData.remoteTrips().toArray(new Trip[]{});
        TripRepository repository = new TripRepository(
                new FakeTripDataSource(), new FakeTripDataSource(trips), scheduler);
        viewModel = createViewModel(repository);
// new TripSearchViewModel(new SavedStateHandle())
    }

    private TripResultsViewModel createViewModel(TripRepository repository) {
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        FilterParams savedFilterParams = new FilterParams.Builder()
                .setKeyword(FILTER_KEYWORD).build();
        savedStateHandle.set(FILTER_PARAMS_SAVED_STATE_KEY, savedFilterParams);
        return new TripResultsViewModel(repository, savedStateHandle, scheduler);
    }

    @After
    public void cleanUp() {
        viewModel.onCleared();
    }

    @Test
    public void isFiltrationInProgress_returnsFalseByDefault() {
        assertThat(viewModel.isFiltrationInProgress, equalTo(false));
    }

    @Test
    public void getShowProgressbar_returnsFalseByDefault() throws InterruptedException {
        boolean showProgressbar = getOrAwaitValue(viewModel.getShowProgressbar());

        assertThat(showProgressbar, equalTo(false));
    }

    @Test
    public void getFilteredResultsCount_returnsZeroByDefault() throws InterruptedException {
        viewModel = createViewModel(new TripRepository(new FakeTripDataSource(), new FakeTripDataSource(), scheduler));
        int filterResultsCount = getOrAwaitValue(viewModel.getFilteredResultsCount());

        assertThat(filterResultsCount, equalTo(0));
    }

    @Test
    public void getTrips_makes_getRemoteTripsFetchResult_returnsSuccess_whenRemoteDataSourceReturnsTrips() throws Exception {
        viewModel.getTrips();

        TaskResult<List<Trip>> taskResult = getOrAwaitValue(viewModel.getRemoteTripsFetchResult());
        boolean showProgressbar = getOrAwaitValue(viewModel.getShowProgressbar());

        assertThat(taskResult, instanceOf(TaskResult.Success.class));
        assertThat(taskResult.getDataOrFail().size(), greaterThanOrEqualTo(1));
        assertThat(showProgressbar, equalTo(false));
    }

    @Test
    public void getTrips_makes_getRemoteTripsFetchResult_returnsSuccess_whenRemoteDataSourceReturnsTrips_emptyTrips() throws Exception {
        viewModel = createViewModel(new TripRepository(new FakeTripDataSource(), new FakeTripDataSource(), scheduler));
        viewModel.getTrips();

        TaskResult<List<Trip>> taskResult = getOrAwaitValue(viewModel.getRemoteTripsFetchResult());
        boolean showProgressbar = getOrAwaitValue(viewModel.getShowProgressbar());

        assertThat(taskResult, instanceOf(TaskResult.Success.class));
        assertThat(taskResult.getDataOrFail().size(), equalTo(0));
        // FIXME: 9/3/20...
//        assertThat(showProgressbar, equalTo(false));
    }

    @Test
    public void setFilterParams_returnsFilteredResultsCount_equalTo_sizeOfFilteredTrips() throws Exception {
        FilterParams filterParams = new FilterParams.Builder()
                .setKeyword(FILTER_KEYWORD)
                .build();

        viewModel.setFilterParams(filterParams);
        int filteredResultsCount = getOrAwaitValue(viewModel.getFilteredResultsCount());
        List<Trip> filteredTrips = getOrAwaitValue(viewModel.getFilteredTrips());
        boolean isFiltrationInProgress = viewModel.isFiltrationInProgress;

        assertThat(filteredResultsCount, equalTo(1));
        assertThat(filteredTrips.size(), equalTo(1));
        assertThat(filteredTrips, contains(FILTERABLE_TRIP));
        assertThat(isFiltrationInProgress, equalTo(false));
    }
}