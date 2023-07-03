package ru.mtsbank.ship.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipRequest {
    private String name;
    private Integer capacity;
}
