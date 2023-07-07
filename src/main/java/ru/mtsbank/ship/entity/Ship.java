package ru.mtsbank.ship.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Ship extends BaseShip {
    private Integer capacity;
}
