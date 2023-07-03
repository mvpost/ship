package ru.mtsbank.ship.thread;

import lombok.Setter;
import ru.mtsbank.ship.model.Ship;
import ru.mtsbank.ship.service.ShipService;

import java.io.IOException;

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

    private void waitUnloading(String url, Ship ship) {
        Thread thread = new Thread(() -> {
            try {
                sleep(5000);
                Integer httpCode = shipService.releaseJetty(url, ship.getName(), ship.getCapacity());
                if (httpCode.equals(200)) {
                    System.out.println("Судно " + ship.getName() + " ёмкостью "
                            + ship.getCapacity() + " освободило причал");
                } else {
                    System.out.println("Судну " + ship.getName() + " не удалось освободить причал");
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
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
                        System.out.println("Судно " + ship.getName() + " ёмкостью "
                                + ship.getCapacity() + " причалило в порту");
                        waitUnloading(url, ship);
                        ship = shipService.createNewShip();
                    } else {
                        System.out.println("Все причалы заняты");
                    }
                }
                sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

