package ru.mtsbank.ship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import ru.mtsbank.ship.request.UrlRequest;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JSONHelper {
    JsonMapper jsonMapper = new JsonMapper();
    private static final String BASE_URL = "http://localhost:8080";
    private static final MediaType MEDIA_TYPE = MediaType
            .parse("application/json; charset=utf-8");

    private String getInitRequestJSON (String name, String type) throws JsonProcessingException {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setName(name);
        urlRequest.setType(type);
        return jsonMapper.writeValueAsString(urlRequest);
    }

    @NotNull
    protected Request buidRequest(String url, String currentCountry) {
        return new Request.Builder()
                .url(BASE_URL + url + currentCountry)
                .build();
    }

    protected Request getInitRequest(String name, String type) throws JsonProcessingException {
        String json = getInitRequestJSON(name, type);
        RequestBody body = RequestBody.create(json, MEDIA_TYPE);
        return new Request.Builder()
                .url(BASE_URL + "/init")
                .post(body)
                .build();
    }

}
