package ru.mtsbank.ship.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryResponse {
    private Integer id;
    private String name;
    private Float latitude;
    private Float longitude;
}

