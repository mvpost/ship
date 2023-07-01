package ru.mtsbank.ship.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FishResponse {
    private String name;
    private Float count;
    private Float cost;
}