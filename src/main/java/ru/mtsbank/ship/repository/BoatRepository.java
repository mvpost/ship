package ru.mtsbank.ship.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.mtsbank.ship.entity.Boat;

@Repository
public class BoatRepository {

    public Boat create(String name) {
        Boat boat = new Boat();
        boat.setName(name);
        boat.setFishCount(10.0f);
        boat.setFishName("Cod");
        boat.setMoney(7.0f);
        return boat;
    }

    public Boat save(@NotNull Boat boat, String fishName, Float fishCount, Float money) {
        boat.setFishName(fishName);
        boat.setFishCount(fishCount);
        boat.setMoney(money);
        return boat;
    }

}

