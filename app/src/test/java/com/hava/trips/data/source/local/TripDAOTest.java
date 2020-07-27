package com.hava.trips.data.source.local;

import android.os.Build;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hava.trips.TripData;
import com.hava.trips.utils.rules.RoomDatabaseRule;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
@Config(sdk = Build.VERSION_CODES.P)
@Ignore("Cannot create room database because of workstation's Sqlite version which does not seem to work with FTS Virtual table(s)")
public class TripDAOTest {

    @Rule
    public RoomDatabaseRule databaseRule = new RoomDatabaseRule();
    private TripDAO dao;

    @Before
    public void setUp() {
        dao = databaseRule.database.getTripDAO();
    }

    @Test
    public void saveTrips_returnsArrayOfLongs_withSizeEqualNumberOfRecordsSaved() {
        Long[] saveTripResults = dao.saveTrips(TripData.TRIP);

        assertThat(saveTripResults.length, equalTo(1));
    }
}