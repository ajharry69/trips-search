package com.hava.trips.data.source.local;

import com.hava.trips.TripData;
import com.hava.trips.data.models.Trip;
import com.hava.trips.fakes.FakeTripDao;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static com.hava.trips.data.Source.REMOTE;

public class TripLocalDataSourceTest {
    private TripLocalDataSource dataSource;
    private TripDAO tripDAO;
    private final Scheduler scheduler = Schedulers.trampoline();

    @Before
    public void setUp() {
        tripDAO = new FakeTripDao();
        dataSource = new TripLocalDataSource(tripDAO);
    }

    @Test
    public void getObservableTrip_withANoneExistingTripID_returnsErrorAndNoValues() {
        populateData();
        TestObserver<Trip> testObserver = dataSource.getObservableTrip(1L, REMOTE)
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertError(NullPointerException.class).assertNoValues();

        testObserver.dispose();
    }

    @Test
    public void getObservableTrip_withExistingTripID_returnsExpectedTripValue() {
        Trip trip = TripData.TRIP;
        populateData(trip);
        TestObserver<Trip> testObserver = dataSource.getObservableTrip(trip.getId(), REMOTE)
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(trip);

        testObserver.dispose();
    }

    @Test
    public void getObservableTrips_returnsEmptyList_whenThereAreNoSavedTrips() {
        populateData();
        TestObserver<List<Trip>> testObserver = dataSource.getObservableTrips()
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(Collections.emptyList());

        testObserver.dispose();
    }

    @Test
    public void getObservableTrips_returnsAllSavedList_whenThereAreSavedTrips() {
        Trip[] trips = getTestTrips();
        populateData(trips);
        TestObserver<List<Trip>> testObserver = dataSource.getObservableTrips()
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(Arrays.asList(trips));
        testObserver.dispose();
    }

    @Test
    public void getObservableTrips_withNullQueryReturns_allSavedLists() {
        assertEmptyOrNullSearchQuery(null);
    }

    @Test
    public void getObservableTrips_withEmptyQueryReturns_allSavedLists() {
        assertEmptyOrNullSearchQuery("");
    }

    @Test
    public void getObservableTrips_withNoneNullOrEmptyQuery_returnsFilteredRecords() {
        Trip trip = TripData.builder(Long.MAX_VALUE).setDriverName("Janet Doe")
                .setDuration(11).build();
        Trip trip1 = TripData.builder(Long.MIN_VALUE).setDriverName("Jane Doe")
                .setDuration(Integer.MAX_VALUE).build();
        populateData(trip, trip1);
        TestObserver<List<Trip>> testObserver = dataSource.getObservableTrips("JAnet DoE")
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(Collections.singletonList(trip));
        testObserver.dispose();
    }

    @Test
    public void savingEmptyTrips_returnsEmptyTrips_withoutErrors() {
        TestObserver<List<Trip>> testObserver = dataSource.saveTrips()
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(Collections.emptyList());
        testObserver.dispose();
    }

    @Test
    public void savingNoneEmptyTrips_returnsPassedTrips() {
        Trip[] trips = getTestTrips();
        TestObserver<List<Trip>> testObserver = dataSource.saveTrips(trips)
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(Arrays.asList(trips));
        testObserver.dispose();
    }

    private void assertEmptyOrNullSearchQuery(String query) {
        Trip[] trips = getTestTrips();
        populateData(trips);
        TestObserver<List<Trip>> testObserver = dataSource.getObservableTrips(query)
                .subscribeOn(scheduler).observeOn(scheduler).test();

        testObserver.assertNoErrors().assertValue(Arrays.asList(trips));
        testObserver.dispose();
    }

    @NotNull
    private Trip[] getTestTrips() {
        return new Trip[]{
                TripData.TRIP,
                TripData.builder(Long.MAX_VALUE).build(),
                TripData.builder(Long.MIN_VALUE).build()
        };
    }

    private void populateData(Trip... trips) {
        ((FakeTripDao) tripDAO).saveTrips(trips);
//        tripDAO.saveTrips(trips);
    }
}