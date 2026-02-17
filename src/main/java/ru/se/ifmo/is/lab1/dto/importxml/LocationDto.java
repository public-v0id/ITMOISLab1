package ru.se.ifmo.is.lab1.dto.importxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LocationDto {

    @XmlElement(required = true)
    private Integer x;

    @XmlElement(required = true)
    private Double y;

    private float z;

    public float getZ() {
        return z;
    }

    public Double getY() {
        return y;
    }

    public Integer getX() {
        return x;
    }
}