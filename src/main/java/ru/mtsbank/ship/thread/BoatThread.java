package ru.mtsbank.ship.thread;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.mtsbank.ship.entity.Boat;
import ru.mtsbank.ship.response.FishResponse;
import ru.mtsbank.ship.service.BoatService;
import java.io.IOException;
@Slf4j
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
                    log.info("Рыбацкой лодке заплатили за рыбу " + fishResponse.getName()
                            + " денег " + fishResponse.getCost());
                    log.info("Всего денег: " + boat.getMoney());
                }
                sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

