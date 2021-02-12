package com.institutotransire.events.services.dataBase;

import com.institutotransire.events.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    protected static Retrofit retrofit;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final long TIMEOUT_CONNECTION_SECONDS = 10;

    public RetrofitConfig() {
        retrofit = config();
    }

    private Retrofit config() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpBuilder.addInterceptor(log);

        httpBuilder.connectTimeout(TIMEOUT_CONNECTION_SECONDS, TimeUnit.SECONDS);

        return new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpBuilder.build())
                .build();
    }
}
