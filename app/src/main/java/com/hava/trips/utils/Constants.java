package com.hava.trips.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {
    public static final String BASE_API_URL = "https://hr.hava.bz/";
    public static final Gson JSON_CONVERTER = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    public static final String TIME_FORMAT = "h:mm a";
    public static final String DATE_FORMAT = "M/d/yyyy";
    public static final String DATE_TIME_FORMAT = String.format("%s %s", DATE_FORMAT, TIME_FORMAT);
    public static final String FILTER_PARAMS_SAVED_STATE_KEY = "FILTER_PARAMS_SAVED_STATE_KEY";

    public static String numberInFixedDPs(@Nullable Number number) {
        Number n = number == null ? 0 : number;
        return String.format(Locale.ROOT, "%,.2f", n.doubleValue());
    }

    public static String getNumber(@Nullable Number number) {
        Number n = number == null ? 0 : number;
        return String.valueOf(Math.round(n.doubleValue()));
    }

    @NonNull
    public static String formattedDateAndOrTime(String dateTime, @NonNull String dateTimePattern, @NonNull String pattern) {
        if (dateTime == null || dateTime.isEmpty()) return "";
        try {
            Locale locale = Locale.getDefault();
            Date date = new SimpleDateFormat(dateTimePattern, locale).parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
            return date == null ? dateTime : dateFormat.format(date);
        } catch (ParseException e) {
            // ignore...
        }
        return dateTime;
    }

    @NonNull
    public static String formattedDateAndOrTime(String dateTime, @NonNull String pattern) {
        return formattedDateAndOrTime(dateTime, "yyyy-MM-dd HH:mm:ss", pattern);
    }
}
