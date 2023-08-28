package ru.mtsbank.ship.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mtsbank.ship.entity.Ship;
import ru.mtsbank.ship.repository.ShipRepository;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class ShipService extends JSONHelper {
    private final ShipRepository repository = new ShipRepository();
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = getBaseUrl();
    private int counter = 1;

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(10 - 1) + 1;
    }

    public Ship create(String name, Integer capacity) { return repository.create(name, capacity); }

    public Ship createNewShip() {
        String name = "Ship" + counter;
        counter++;
        int capacity = getRandomNumber();
        return repository.create(name, capacity);
    }

    public String requestShipURL(String type, Integer capacity) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/location/" + type + "?capacity=" + capacity)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.code() != 200) {
            throw new IOException();
        }
        return Objects.requireNonNull(response.body()).string();
    }

    public void addShip(String url, String shipName, Integer capacity, String jettyName) {
        String serverURL = BASE_URL + url;
        File file = new File("/home/onix/Рабочий стол/kontejner.jpg");

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpg"), file))
                .addFormDataPart("name", shipName)
                .addFormDataPart("capacity", String.valueOf(capacity))
                .addFormDataPart("jettyName", jettyName)
                .build();

        Request request = new Request.Builder()
                .url(serverURL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {}

            @Override
            public void onResponse(@NotNull final Call call, @NotNull final Response response) {
                if (!response.isSuccessful()) {
                    log.info("Ошибка при передаче файла: " + response.message());
                }
            }
        });
    }
}
