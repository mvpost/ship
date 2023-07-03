package ru.mtsbank.ship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mtsbank.ship.model.Ship;
import ru.mtsbank.ship.repository.ShipRepository;
import ru.mtsbank.ship.request.ShipRequest;

import java.io.IOException;
import java.util.Random;

@Service
public class ShipService extends JSONHelper {
    private final ShipRepository repository = new ShipRepository();
    private final OkHttpClient client = new OkHttpClient();
    private final JsonMapper jsonMapper = new JsonMapper();
    private static final String BASE_URL = getBaseUrl();
    private static final MediaType MEDIA_TYPE = getMediaType();
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

    private String getRequestJSON (String name, Integer capacity) throws JsonProcessingException {
        ShipRequest shipRequest = new ShipRequest();
        shipRequest.setName(name);
        shipRequest.setCapacity(capacity);
        return jsonMapper.writeValueAsString(shipRequest);
    }

    @NotNull
    private Request buidJettyRequest(String url, String shipName, Integer capacity) throws JsonProcessingException {
        String json = getRequestJSON(shipName, capacity);
        RequestBody body = RequestBody.create(json, MEDIA_TYPE);
        return new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();
    }

    @NotNull
    private Request buidJettyReleaseRequest(String url, String shipName, Integer capacity) throws JsonProcessingException {
        String json = getRequestJSON(shipName, capacity);
        RequestBody body = RequestBody.create(json, MEDIA_TYPE);
        return new Request.Builder()
                .url(BASE_URL + url)
                .delete(body)
                .build();
    }

    @NotNull
    public Integer releaseJetty(String url, String shipName, Integer capacity) throws IOException {
        Request request = buidJettyReleaseRequest(url, shipName, capacity);
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.code();
    }

    public Integer requestJetty(String url, String shipName, Integer capacity) throws IOException {
        Request request = buidJettyRequest(url, shipName, capacity);
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.code();
    }
}
