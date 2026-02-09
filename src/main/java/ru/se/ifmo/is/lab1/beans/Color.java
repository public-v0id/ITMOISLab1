package ru.se.ifmo.is.lab1.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Color {
    @JsonProperty("RED") RED,
    @JsonProperty("BLUE") BLUE,
    @JsonProperty("BROWN") BROWN
}