package ru.mtsbank.ship;

import ru.mtsbank.ship.thread.BoatThread;
import ru.mtsbank.ship.thread.YachtThread;

public class ShipApplication {

	public static void main(String[] args) {

		waitShutdown();

		String shipName = args[0];
		String shipType = args[1];

		if (shipName != null && shipType != null) {
            switch (shipType) {
                case "yacht" -> yachtTrip(shipName);
                case "boat" -> boatFishing(shipName);
                case "ship" -> portStop(shipName);
                default -> System.out.println("Неизвестный тип корабля");
            }
		}
	}

	private static void yachtTrip(String shipName) {
		YachtThread yachtTread = new YachtThread(shipName);
		yachtTread.start();
	}

	private static void boatFishing(String boatName) {
		BoatThread boatTread = new BoatThread(boatName);
		boatTread.start();
	}

	private static void portStop(String shipName) {
		System.out.println("Прибыло судно " + shipName);
	}

	private static void waitShutdown() {
		var shutdownListener = new Thread(ShipApplication::run);
		Runtime.getRuntime().addShutdownHook(shutdownListener);
	}

	private static void run() {
		System.out.println("Судно завершает работу");
	}
}
