package ru.mtsbank.ship.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Yacht extends BaseShip {
    @NotNull
    private String country;
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
}
