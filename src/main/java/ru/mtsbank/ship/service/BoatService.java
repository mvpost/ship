package ru.mtsbank.ship.service;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mtsbank.ship.entity.Boat;
import ru.mtsbank.ship.repository.BoatRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@Service
public class BoatService extends JSONHelper {
    private final BoatRepository boatRepository = new BoatRepository();
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = getBaseUrl();

    @NotNull
    private Request buildFishRequest(String url, String fishName, Float fishCount) {
        HttpUrl.Builder urlBuilder
                = Objects.requireNonNull(HttpUrl.parse(BASE_URL + url)).newBuilder();
        urlBuilder.addQueryParameter("fishName", fishName);
        urlBuilder.addQueryParameter("fishCount", fishCount.toString());
        String resultUrl = urlBuilder.build().toString();
        return new Request.Builder()
                .url(resultUrl)
                .build();
    }

    private static float getRandomNumber() {
        Random random = new Random();
        return random.nextFloat(100.0f - 1.0f) + 1.0f;
    }

    private static String getRandomString() {
        int r = (int) (Math.random() * 3);
        return new String [] {"Cod","Trout","Sprat"}[r];
    }

    public Boat create(String name) { return boatRepository.create(name); }

    public Boat save(Boat boat, float fishCost) {
        return boatRepository.save(boat,
                boat.getFishName(),
                boat.getFishCount(),
                boat.getMoney() + fishCost);
    }

    public Boat getFishingResult(Boat boat) {
        return boatRepository.save(boat,
                getRandomString(),
                getRandomNumber(),
                boat.getMoney());
    }

    public float requestMoney(String url, String fishName, Float fishCount) throws IOException {
        Request request = buildFishRequest(url, fishName, fishCount);
        Call call = client.newCall(request);
        Response response = call.execute();
        return Float.parseFloat(Objects.requireNonNull(response.body()).string());
    }
}

