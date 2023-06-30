package ru.mtsbank.ship.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Boat extends BaseShip {
    private String fishName;
    private Float fishCount;
}
