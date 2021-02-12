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
    @GET("/api/events")
    Call<List<Events>> getEvents();

    @GET("/api/events/{id}")
    Call<Events> geEvent(
            @Path("id") int id
    );

    @POST("/checkin")
    Call<Void> checkin(
            @Body DetailsEvents detailsEvents);
}