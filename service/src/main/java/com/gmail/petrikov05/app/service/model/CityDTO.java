package com.gmail.petrikov05.app.service.model;

import java.util.UUID;

import lombok.Data;

@Data
public class CityDTO {

    private UUID id;
    private String name;
    private String info;

}
