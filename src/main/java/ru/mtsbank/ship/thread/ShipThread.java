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
        while (isRunning) {
            try {
                String url = shipService.requestShipURL("ship", ship.getCapacity());
                String jettyName = url.substring(url.lastIndexOf("/") + 1);
                url = url.substring(0, url.lastIndexOf("/"));
                shipService.addShip(url, ship.getName(), ship.getCapacity(), jettyName);
                log.info("Судно %s ёмкостью %s разгрузилось на причале %s".formatted(ship.getName(), ship.getCapacity(), jettyName));
                ship = shipService.createNewShip();
                sleep(500);
            } catch (IOException | InterruptedException e) {
                log.info("Причал занят");
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}

