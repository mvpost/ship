package ru.mtsbank.ship.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mtsbank.ship.response.CountryResponse;
import ru.mtsbank.ship.model.Yacht;
import ru.mtsbank.ship.repository.YachtRepository;
import ru.mtsbank.ship.response.UrlResponse;

import java.io.IOException;
import java.util.Objects;

@Service
public class YachtService extends JSONHelper {
    private final YachtRepository repository = new YachtRepository();
    OkHttpClient client = new OkHttpClient();
    JsonMapper jsonMapper = new JsonMapper();

    public Yacht create(String name) { return repository.create(name); }

    public Yacht save(Yacht yacht, @NotNull CountryResponse countryResponse) {
        return repository.save(yacht,
                countryResponse.getName(),
                countryResponse.getLatitude(),
                countryResponse.getLongitude());
    }

    public String requestURL(String yachtName) throws IOException {
        Request request = getInitRequest(yachtName, "yacht");
        Call call = client.newCall(request);
        Response response = call.execute();
        return jsonMapper.readValue(Objects.requireNonNull(response.body()).string(), UrlResponse.class)
                .getLocation();
    }

    public CountryResponse requestCountry(String url, String currentCountry) throws IOException {
        Request request = buidRequest(url, currentCountry);
        Call call = client.newCall(request);
        Response response = call.execute();
        return jsonMapper.readValue(Objects.requireNonNull(response.body()).string(), CountryResponse.class);
    }
}
