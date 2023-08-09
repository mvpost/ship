package ru.mtsbank.ship.service;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Service
public class JSONHelper {
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL;

    static {
        try {
            BASE_URL = getProperty("host") + ":" + getProperty("port");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String getBaseUrl() {
        return BASE_URL;
    }

    @NotNull
    protected Request buidRequest(String url, String currentCountry) {
        return new Request.Builder()
                .url(BASE_URL + url + currentCountry)
                .build();
    }

    public String requestURL(String type) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/location/" + type)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return Objects.requireNonNull(response.body()).string();
    }

    private static String getProperty(String propertyName) throws IOException {
        String propertyValue;
        File configFile = new File("src/main/resources/application.properties");
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);
        propertyValue = props.getProperty(propertyName);
        reader.close();
        return propertyValue;
    }

}
