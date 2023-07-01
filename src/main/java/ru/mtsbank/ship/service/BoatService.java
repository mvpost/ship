package ru.mtsbank.ship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mtsbank.ship.model.Boat;
import ru.mtsbank.ship.repository.BoatRepository;
import ru.mtsbank.ship.request.FishRequest;
import ru.mtsbank.ship.response.FishResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@Service
public class BoatService extends JSONHelper {
    private final BoatRepository repository = new BoatRepository();
    private final OkHttpClient client = new OkHttpClient();
    private final JsonMapper jsonMapper = new JsonMapper();
    private static final String BASE_URL = getBaseUrl();
    private static final MediaType MEDIA_TYPE = getMediaType();

    private String getRequestJSON (String name, Float count) throws JsonProcessingException {
        FishRequest fishRequest = new FishRequest();
        fishRequest.setName(name);
        fishRequest.setCount(count);
        return jsonMapper.writeValueAsString(fishRequest);
    }

    @NotNull
    private Request buidFishRequest(String url, String fishName, Float fishCount) throws JsonProcessingException {
        String json = getRequestJSON(fishName, fishCount);
        RequestBody body = RequestBody.create(json, MEDIA_TYPE);
        return new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();
    }

    private float getRandomNumber() {
        Random random = new Random();
        return random.nextFloat(100.0f - 1.0f) + 1.0f;
    }

    static String getRandomString() {
        int r = (int) (Math.random()*3);
        return new String [] {"Cod","Trout","Sprat"}[r];
    }

    public Boat create(String name) { return repository.create(name); }

    public Boat save(Boat boat, @NotNull FishResponse fishResponse) {
        return repository.save(boat,
                fishResponse.getName(),
                fishResponse.getCount(),
                boat.getMoney() + fishResponse.getCost());
    }

    public Boat getFishingResult(Boat boat) {
        return repository.save(boat,
                getRandomString(),
                getRandomNumber(),
                boat.getMoney());
    }

    public FishResponse requestMoney(String url, String fishName, Float fishCount) throws IOException {
        Request request = buidFishRequest(url, fishName, fishCount);
        Call call = client.newCall(request);
        Response response = call.execute();
        return jsonMapper.readValue(Objects.requireNonNull(response.body()).string(), FishResponse.class);
    }
}

