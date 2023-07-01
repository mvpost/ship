package ru.mtsbank.ship.thread;

import lombok.Setter;
import ru.mtsbank.ship.model.Boat;
import ru.mtsbank.ship.response.FishResponse;
import ru.mtsbank.ship.service.BoatService;
import java.io.IOException;

public class BoatThread extends Thread {
    @Setter
    private String shipName;
    private volatile boolean isRunning = true;
    BoatService boatService = new BoatService();

    public BoatThread(String shipName) {
        this.shipName = shipName;
    }

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        Boat boat = boatService.create(shipName);
        try {
            String url = boatService.requestURL(boat.getName(), "boat");

            while (isRunning) {
                if (url != null) {
                    boat = boatService.getFishingResult(boat);
                    FishResponse fishResponse = boatService.requestMoney(url, boat.getFishName(), boat.getFishCount());
                    boat = boatService.save(boat, fishResponse);
                    System.out.println("Рыбацкой лодке заплатили за рыбу " + fishResponse.getName()
                            + " денег " + fishResponse.getCost());
                    System.out.println("Всего денег: " + boat.getMoney());
                }
                sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

