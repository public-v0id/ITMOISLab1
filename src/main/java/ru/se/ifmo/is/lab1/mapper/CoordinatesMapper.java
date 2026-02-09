package ru.se.ifmo.is.lab1.mapper;

import ru.se.ifmo.is.lab1.beans.Coordinates;
import ru.se.ifmo.is.lab1.dto.CoordinatesDTO;

public class CoordinatesMapper {
    public static CoordinatesDTO toDTO(Coordinates c) {
        if (c == null) return null;
        CoordinatesDTO dto = new CoordinatesDTO();
        dto.id = c.getId();
        dto.x = c.getX();
        dto.y = c.getY();
        return dto;
    }

    public static Coordinates fromDTO(CoordinatesDTO dto) {
        if (dto == null) return null;
        Coordinates c = new Coordinates();
        c.setId(dto.id);
        c.setX(dto.x);
        c.setY(dto.y);
        return c;
    }
}
