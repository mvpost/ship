package ru.mtsbank.ship.thread;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.mtsbank.ship.entity.Ship;
import ru.mtsbank.ship.service.ShipService;

import java.io.IOException;
@Slf4j
public class ShipThread extends Thread {
    @Setter
    private String shipName;
    private volatile boolean isRunning = true;
    ShipService shipService = new ShipService();

    public ShipThread(String shipName) {
        this.shipName = shipName;
    }

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        Ship ship = shipService.create(shipName, 1);
        try {
            String url = shipService.requestURL(ship.getName(), "ship");

            while (isRunning) {
                if (url != null) {
                    Integer httpCode = shipService.requestJetty(url, ship.getName(), ship.getCapacity());
                    if (httpCode.equals(200)) {
                        log.info("Судно " + ship.getName() + " ёмкостью "
                                + ship.getCapacity() + " причалило в порту");
                        shipService.uploadFile(url);
                        ship = shipService.createNewShip();
                    } else {
                        log.info("Все причалы заняты");
                    }
                }
                sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

