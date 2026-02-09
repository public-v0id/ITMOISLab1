package ru.se.ifmo.is.lab1.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Country {
    @JsonProperty("RUSSIA") RUSSIA,
    @JsonProperty("USA") USA,
    @JsonProperty("SOUTH_KOREA") SOUTH_KOREA,
    @JsonProperty("JAPAN") JAPAN;
}