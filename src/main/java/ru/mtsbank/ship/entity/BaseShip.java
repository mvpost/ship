package ru.mtsbank.ship.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseShip {
    @NotNull
    private String name;
}
