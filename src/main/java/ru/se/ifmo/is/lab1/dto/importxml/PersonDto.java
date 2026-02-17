package ru.se.ifmo.is.lab1.dto.importxml;

import ru.se.ifmo.is.lab1.beans.Color;
import ru.se.ifmo.is.lab1.beans.Coordinates;
import ru.se.ifmo.is.lab1.beans.Country;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDto {

    @XmlElement(required = true)
    private String name;

    private Color eyeColor;

    @XmlElement(required = true)
    private Color hairColor;

    private LocationDto location;

    @XmlElement(required = true)
    private String passportID;

    private Country nationality;

    public LocationDto getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPassportID() {
        return passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Color getEyeColor() {
        return eyeColor;
    }
}