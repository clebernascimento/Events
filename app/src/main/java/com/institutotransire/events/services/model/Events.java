package com.institutotransire.events.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Events {
    private int id;
    private String title;
    private long date;
    private String image;
    private String description;
    private Double longitude;
    private Double latitude;
    private Double price;
}
