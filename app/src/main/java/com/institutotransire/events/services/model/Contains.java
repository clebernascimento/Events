package com.institutotransire.events.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contains {
    public static int id;
    private String title;
    private long date;
    private String imageEvents;
    private String description;
    private Double longitude;
    private Double latitude;
    private Double price;
}
