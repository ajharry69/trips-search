package com.hava.trips.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConstantsTest {

    @Test
    public void numberInFixedDPs() {
        String n1 = Constants.numberInFixedDPs(0);
        String n2 = Constants.numberInFixedDPs(1);
        String n3 = Constants.numberInFixedDPs(1.44);
        String n4 = Constants.numberInFixedDPs(1254.554);
        String n5 = Constants.numberInFixedDPs(1254.555);
        String n6 = Constants.numberInFixedDPs(null);

        assertThat(n1, equalTo("0.00"));
        assertThat(n2, equalTo("1.00"));
        assertThat(n3, equalTo("1.44"));
        assertThat(n4, equalTo("1,254.55"));
        assertThat(n5, equalTo("1,254.56"));
        assertThat(n6, equalTo("0.00"));
    }

    @Test
    public void getNumber() {
        String n1 = Constants.getNumber(0);
        String n2 = Constants.getNumber(1);
        String n3 = Constants.getNumber(1.44);
        String n4 = Constants.getNumber(1254.554);
        String n5 = Constants.getNumber(1254.555);
        String n6 = Constants.getNumber(null);

        assertThat(n1, equalTo("0"));
        assertThat(n2, equalTo("1"));
        assertThat(n3, equalTo("1"));
        assertThat(n4, equalTo("1255"));
        assertThat(n5, equalTo("1255"));
        assertThat(n6, equalTo("0"));
    }

    @Test
    public void formattedDateAndOrTime() {
        String d1 = Constants.formattedDateAndOrTime(null, "dd-MM-yyyy");
        String d2 = Constants.formattedDateAndOrTime("", "dd-MM-yyyy");
        // uses default date time format yyyy-MM-dd HH:mm:ss
        String d3 = Constants.formattedDateAndOrTime("2020-07-26 16:09:56", "h:mm");
        String d4 = Constants.formattedDateAndOrTime("2020/07-26 4:09 PM", "yyyy/MM-dd h:mm a", "h:mm a");
        // date time and date time format does not match
        String d5 = Constants.formattedDateAndOrTime("2020/07-26 4:09 PM", "yyyy-MM-dd h:mm a", "h:mm a");

        assertThat(d1, equalTo(""));
        assertThat(d2, equalTo(""));
        assertThat(d3, equalTo("4:09"));
        assertThat(d4, equalTo("4:09 PM"));
        assertThat(d5, equalTo("2020/07-26 4:09 PM"));
    }
}