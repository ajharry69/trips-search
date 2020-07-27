package com.hava.trips.di.modules.datasource.remote;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hava.trips.utils.Constants.BASE_API_URL;
import static com.hava.trips.utils.Constants.JSON_CONVERTER;

@Module
@InstallIn(ApplicationComponent.class)
public class RemoteDataSourceModule {
    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Not suitable for release
        interceptor.redactHeader("Authorization");
        return interceptor;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(JSON_CONVERTER))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(okHttpClient)
                .build();
    }
}
