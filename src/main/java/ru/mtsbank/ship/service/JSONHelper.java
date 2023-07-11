package ru.mtsbank.ship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mtsbank.ship.request.UrlRequest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ru.mtsbank.ship.response.UrlResponse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Service
public class JSONHelper {
    private final JsonMapper jsonMapper = new JsonMapper();
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = getProperty("host") + ":" + getProperty("port");
    private static final MediaType MEDIA_TYPE = MediaType
            .parse(getProperty("mediaType"));

    private String getInitRequestJSON (String name, String type) throws JsonProcessingException {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setName(name);
        urlRequest.setType(type);
        return jsonMapper.writeValueAsString(urlRequest);
    }

    protected static String getBaseUrl() {
        return BASE_URL;
    }

    protected static MediaType getMediaType() {
        return MEDIA_TYPE;
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
                .url(BASE_URL + "/location")
                .post(body)
                .build();
    }

    public String requestURL(String boatName, String type) throws IOException {
        Request request = getInitRequest(boatName, type);
        Call call = client.newCall(request);
        Response response = call.execute();
        return jsonMapper.readValue(Objects.requireNonNull(response.body()).string(), UrlResponse.class)
                .getLocation();
    }

    private static String getProperty(String propertyName) {
        String propertyValue = "";
        try {
            File configFile = new File("src/main/resources/application.properties");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            propertyValue = props.getProperty(propertyName);
            reader.close();
        } catch (IOException ex) {}
        return propertyValue;
    }

}
