package ru.mtsbank.ship.thread;

import lombok.Setter;
import ru.mtsbank.ship.entity.Yacht;
import ru.mtsbank.ship.response.CountryResponse;
import ru.mtsbank.ship.service.YachtService;
import java.io.IOException;

public class YachtThread extends Thread {
    @Setter
    private String shipName;
    private volatile boolean isRunning = true;
    YachtService yachtService = new YachtService();

    public YachtThread(String shipName) {
        this.shipName = shipName;
    }

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        Yacht yacht = yachtService.create(shipName);
        try {
            String url = yachtService.requestURL(yacht.getName(), "yacht");

            while (isRunning) {
                if (url != null) {
                    CountryResponse countryResponse = yachtService.requestCountry(url, yacht.getCountry());
                    yacht = yachtService.save(yacht, countryResponse);
                    System.out.println("Яхта " + yacht.getName() + " отправилась в страну " + yacht.getCountry());
                }
                sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
