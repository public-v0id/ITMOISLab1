package ru.se.ifmo.is.lab1.dto.importxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CoordinatesDto {

    @XmlElement(required = true)
    private Double x;

    @XmlElement(required = true)
    private Integer y;

    public Integer getY() {
        return y;
    }

    public Double getX() {
        return x;
    }
}
