package ru.mtsbank.ship.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Ship extends BaseShip {
    private Integer capacity;
}
