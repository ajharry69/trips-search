package com.hava.trips.data.repository;

import com.hava.trips.R;
import com.hava.trips.TripData;
import com.hava.trips.data.Source;
import com.hava.trips.data.models.FilterParams;
import com.hava.trips.data.models.Trip;
import com.hava.trips.data.models.Trip.Status;
import com.hava.trips.fakes.FakeTripDataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Scheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static com.hava.trips.data.Source.LOCAL;
import static com.hava.trips.data.Source.REMOTE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class TripRepositoryTest {

    private TripRepository repository;
    private FakeTripDataSource localDataSource;
    private FakeTripDataSource remoteDataSource;
    private Scheduler testScheduler = Schedulers.trampoline();

    @Before
    public void setUp() {
        localDataSource = new FakeTripDataSource();
        remoteDataSource = new FakeTripDataSource(TripData.remoteTrips().toArray(new Trip[]{}));
        repository = new TripRepository(localDataSource, remoteDataSource, testScheduler);
    }

    @Test
    public void getObservableTrip_fromRemoteSource_returnsExpectedTrip_forAnExistingTripRecord() {
        // Given
        Trip trip = TripData.TRIP;

        // When
        TestObserver<Trip> observer = repository.getObservableTrip(trip.getId(), REMOTE)
                .subscribeOn(testScheduler).observeOn(testScheduler).test();

        // Then
        observer.assertValueCount(1).assertResult(trip);
        observer.dispose();
    }

    @Test
    public void getObservableTrip_fromRemoteSource_throwsNullPointerException_forUnExistingTripRecord() {
        assertUnexistingTripFetch(REMOTE);
    }

    @Test
    public void getObservableTrip_fromLocalSource_throwsNullPointerException_forUnExistingTripRecord() {
        assertUnexistingTripFetch(LOCAL);
    }

    @Test
    public void saveTrips_savesTripsRemotelyThenLocally() {
        // Given
        Trip trip = TripData.createTrip(Long.MAX_VALUE);

        // When
        TestObserver<List<Trip>> observer = repository.saveTrips(trip)
                .subscribeOn(testScheduler).observeOn(testScheduler).test();

        // Then
        int localTripsCount = localDataSource.getTripsCount();
        int remoteTripsCount = remoteDataSource.getTripsCount();
        assertThat(remoteTripsCount, greaterThanOrEqualTo(localTripsCount));
        assertThat(localTripsCount, equalTo(1));
        observer.assertValueCount(localTripsCount);
        observer.dispose();
    }

    @Test
    public void getObservableTrips_withNoneNullSearchQuery_onlyReturnsMatchedResults() {
        String searchQuery = "John Doe New";
        Trip trip = TripData.builder(Long.MAX_VALUE)
                .setDriverName(searchQuery)
                .build();

        assertTripsSearchQueryResultsIsSuccess(searchQuery, trip);
    }

    @Test
    public void getObservableTrips_doesACaseInsensitiveSearch() {
        String searchQuery = "John Doe New";
        Trip trip = TripData.builder(Long.MAX_VALUE)
                .setDriverName(searchQuery)
                .build();

        assertTripsSearchQueryResultsIsSuccess("joHn doe NeW", trip);
    }

    @Test
    public void getObservableTrips_cachesAndReturnsTheReturnedRemoteTripRecords() {
        TestObserver<List<Trip>> observer = repository.getObservableTrips()
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .test();

        assertThat(remoteDataSource.getTripsCount(), equalTo(localDataSource.getTripsCount()));
        observer.assertNoErrors();

        observer.dispose();
    }

    @Test
    public void filterByTime_returnsRecordsMatching_requestedTime() {
        Set<Trip> trips = new HashSet<>();
        Trip trip = TripData.builder(1L).setDuration(10).build();
        trips.add(trip);
        trips.add(TripData.builder(2L).setDuration(20).build());
        trips.add(TripData.builder(3L).setDuration(21).build());

        FilterParams filterParams = new FilterParams.Builder()
                .setTime(R.id.time_10_to_20).build();

        Set<Trip> filterResults = repository.filterByTime(trip, filterParams, trips);

        assertThat(filterResults.size(), equalTo(2));
    }

    @Test
    public void filterByDistance_returnsRecordsMatching_requestedDistance() {
        Set<Trip> trips = new HashSet<>();
        Trip trip = TripData.builder(1L).setDistance(8).build();
        trips.add(trip);
        trips.add(TripData.builder(2L).setDistance(15).build());
        trips.add(TripData.builder(3L).setDistance(16).build());

        FilterParams filterParams = new FilterParams.Builder()
                .setDistance(R.id.distance_8_to_15).build();

        Set<Trip> filterResults = repository.filterByDistance(trip, filterParams, trips);

        assertThat(filterResults.size(), equalTo(2));
    }

    @Test
    public void filterTrips_includingCancelledTrips_returnsAllTripRecords() {
        assertFilterTripsByTripStatus(true, 3);
    }

    @Test
    public void filterTrips_withoutIncludingCancelledTrips_returnsOnlyCompletedTripRecords() {
        assertFilterTripsByTripStatus(false, 2);
    }

    @Test
    public void getObservableTrips_withFilterParams_passedAsParameter() {
        Trip trip = TripData.builder(Long.MAX_VALUE).setDriverName("Jane Doe")
                .setDuration(11).build();
        Trip trip1 = TripData.builder(Long.MIN_VALUE).setDriverName("Jane Doe")
                .setDuration(Integer.MAX_VALUE).build();
        // appending test is necessary for caching retrieved record(s)
        repository.saveTrips(trip, trip1).test();
        repository.getObservableTrips().test();

        FilterParams filterParams = new FilterParams.Builder()
                .setKeyword("jAne doE")
                .setTime(R.id.time_10_to_20) // filters out trip1
                .build();
        TestObserver<List<Trip>> testObserver = repository.getObservableTrips(filterParams)
                .subscribeOn(testScheduler).observeOn(testScheduler).test();

        List<Trip> filteredTrips = new ArrayList<>();
        filteredTrips.add(trip);
        testObserver.assertNoErrors().assertValue(filteredTrips);

        testObserver.dispose();
    }

    /**
     * Do some cleanup afterwards
     */
    private void assertFilterTripsByTripStatus(boolean includeCancelled, int filterResultsCount) {
        Set<Trip> trips = new HashSet<>();
        Trip trip = TripData.builder(1L).setStatus(Status.CANCELLED).build();
        trips.add(trip);
        trips.add(TripData.builder(2L).setStatus(Status.COMPLETED).build());
        trips.add(TripData.builder(3L).setStatus(Status.COMPLETED).build());

        FilterParams filterParams = new FilterParams.Builder()
                .setIncludeCancelled(includeCancelled).build();

        Set<Trip> filterResults = repository.filterByStatus(trip, filterParams, trips);

        assertThat(filterResults.size(), equalTo(filterResultsCount));
    }

    private void assertTripsSearchQueryResultsIsSuccess(String searchQuery, Trip trip) {
        repository.saveTrips(trip);

        TestObserver<List<Trip>> observer = repository.getObservableTrips(searchQuery)
                .subscribeOn(testScheduler).observeOn(testScheduler).test();

        List<Trip> expectedSearchResult = new ArrayList<>();
        expectedSearchResult.add(trip);

        // Then - search results contains only 1 expected element
        observer.assertValueCount(1).assertValue(expectedSearchResult);
        observer.dispose();
    }

    private void assertUnexistingTripFetch(Source local) {
        // Given
        Long tripId = Long.MIN_VALUE;

        // When
        TestObserver<Trip> observer = repository.getObservableTrip(tripId, local)
                .subscribeOn(testScheduler).observeOn(testScheduler).test();

        // Then
        observer.assertError(NullPointerException.class).assertValueCount(0);
        observer.dispose();
    }
}