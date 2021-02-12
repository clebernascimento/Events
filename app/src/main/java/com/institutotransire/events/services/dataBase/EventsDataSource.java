package com.institutotransire.events.services.dataBase;

import com.institutotransire.events.services.api.EventsController;
import com.institutotransire.events.services.model.Contains;
import com.institutotransire.events.services.model.DetailsEvents;
import com.institutotransire.events.services.model.Events;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class EventsDataSource extends RetrofitConfig{
    private static EventsDataSource instance;
    private EventsController api;

    public static EventsDataSource getInstance() {
        if (instance == null) {
            instance = new EventsDataSource();
        }
        return instance;
    }

    public EventsDataSource() {
        setApi();
    }


    public void setApi() {
        api = retrofit.create(EventsController.class);
    }

    public void listEvents(Callback<List<Events>> callback){
        Call<List<Events>> call = api.getEvents();
        call.enqueue(callback);
    }

    public void listEventId(int id, Callback<Events> callback){
        Call<Events> call = api.geEvent(id);
        call.enqueue(callback);
    }

    public void setCheckin(int id, String name, String email, Callback<Void> callback){
        Call<Void> call = api.setCheckin(id, name, email);
        call.enqueue(callback);
    }

    public void checkin(DetailsEvents detailsEvents, Callback<Void> callback){
        Call<Void> call = api.checkin(detailsEvents);
        call.enqueue(callback);
    }
}
