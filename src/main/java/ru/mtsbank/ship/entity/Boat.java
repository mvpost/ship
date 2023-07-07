package ru.mtsbank.ship.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Boat extends BaseShip {
    private String fishName;
    @NotNull
    private Float fishCount;
    @NotNull
    private Float money;
}
