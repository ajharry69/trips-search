package com.hava.trips.ui.search;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.SavedStateHandle;

import com.hava.trips.R;
import com.hava.trips.data.models.FilterParams;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.hava.trips.LiveDataTestUtils.getOrAwaitValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TripSearchViewModelTest {

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();
    private TripSearchViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TripSearchViewModel(new SavedStateHandle());
    }

    @Test
    public void isFilterParamsSaved_returnsFalseByDefault() throws InterruptedException {
        assertThat(getOrAwaitValue(viewModel.isFilterParamsSaved()), equalTo(false));
    }

    @Test
    public void isNavigateToResultsPage_returnsFalseByDefault() throws InterruptedException {
        assertThat(getOrAwaitValue(viewModel.isNavigateToResultsPage()), equalTo(false));
    }

    @Test
    public void saveFilterParams_makes_isNavigateToResultsPage_returnTrue() throws InterruptedException {
        viewModel.saveFilterParams(FilterParams.DEFAULT);
        boolean isNavigateToResultsPage = getOrAwaitValue(viewModel.isNavigateToResultsPage());
        assertThat(isNavigateToResultsPage, equalTo(true));
    }

    @Test
    public void setIsNavigateToResultsPage_makes_isNavigateToResultsPage_returnThePassedValue() throws InterruptedException {
        viewModel.setIsNavigateToResultsPage(false);
        assertThat(getOrAwaitValue(viewModel.isNavigateToResultsPage()), equalTo(false));
        viewModel.setIsNavigateToResultsPage(true);
        assertThat(getOrAwaitValue(viewModel.isNavigateToResultsPage()), equalTo(true));
    }

    @Test
    public void getObservableFilterParams_withoutSavingFilterParams_returnDefaultFilterParams() throws InterruptedException {
        assertThat(getOrAwaitValue(viewModel.getObservableFilterParams()), equalTo(FilterParams.DEFAULT));
    }

    @Test
    public void saveFilterParams_makes_isFilterParamsSaved_returnsTrue_and_getObservableFilterParams_returnSavedFilterParams() throws InterruptedException {
        FilterParams filterParams = new FilterParams.Builder()
                .setDistance(R.id.distance_8_to_15).build();

        viewModel.saveFilterParams(filterParams);

        boolean isFilterParamSaved = getOrAwaitValue(viewModel.isFilterParamsSaved());
        boolean isNavigateToResultsPage = getOrAwaitValue(viewModel.isNavigateToResultsPage());
        FilterParams savedFilterParams = getOrAwaitValue(viewModel.getObservableFilterParams());

        assertThat(isNavigateToResultsPage, equalTo(true));
        assertThat(isFilterParamSaved, equalTo(true));
        assertThat(savedFilterParams, notNullValue());
        assertThat(savedFilterParams.getDistance(), equalTo(R.id.distance_8_to_15));
    }
}