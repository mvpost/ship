package ru.mtsbank.ship.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrlResponse {
    private String name;
    private String guid;
    private String location;
}
