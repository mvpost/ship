package ru.mtsbank.ship.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.mtsbank.ship.entity.Ship;

@Repository
public class ShipRepository {

    public Ship create(String name, Integer capacity) {
        Ship ship = new Ship();
        ship.setName(name);
        ship.setCapacity(capacity);
        return ship;
    }

    public Ship save(@NotNull Ship ship, Integer capacity) {
        ship.setCapacity(capacity);
        return ship;
    }

}

