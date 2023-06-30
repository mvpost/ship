package ru.mtsbank.ship.repository;

import org.jetbrains.annotations.NotNull;
import ru.mtsbank.ship.model.Yacht;
import org.springframework.stereotype.Repository;

@Repository
public class YachtRepository {

    public Yacht create(String name) {
        Yacht yacht = new Yacht();
        yacht.setName(name);
        yacht.setCountry("Barbados");
        yacht.setLat(10.1010f);
        yacht.setLon(20.2020f);
        return yacht;
    }

    public Yacht save(@NotNull Yacht yacht, String countryName, Float lat, Float lon) {
        yacht.setCountry(countryName);
        yacht.setLat(lat);
        yacht.setLon(lon);
        return yacht;
    }

}
