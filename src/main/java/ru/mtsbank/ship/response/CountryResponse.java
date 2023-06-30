package ru.mtsbank.ship.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryResponse {
    private String name;
    private Float latitude;
    private Float longitude;
}

