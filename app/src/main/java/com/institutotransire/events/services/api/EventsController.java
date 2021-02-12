package com.institutotransire.events.services.api;

import com.institutotransire.events.services.model.Contains;
import com.institutotransire.events.services.model.DetailsEvents;
import com.institutotransire.events.services.model.Events;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsController {
    @GET("events")
    Call<List<Events>> getEvents();

    @GET("events/{id}")
    Call<Events> geEvent(
            @Path("id") int id
    );

    @POST("checkin")
    Call<Void> setCheckin(
        @Query("checkin") int id,
        @Query("name") String name,
        @Query("email") String email
    );

    @POST("checkin")
    Call<Void> checkin(
            @Body DetailsEvents detailsEvents);
}
