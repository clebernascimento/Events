package com.institutotransire.events.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailsEvents {
    private int eventId;
    private String name;
    private String email;
}
