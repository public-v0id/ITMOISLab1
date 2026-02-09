package ru.se.ifmo.is.lab1.mapper;

import ru.se.ifmo.is.lab1.beans.Coordinates;
import ru.se.ifmo.is.lab1.beans.Location;
import ru.se.ifmo.is.lab1.dto.CoordinatesDTO;
import ru.se.ifmo.is.lab1.dto.LocationDTO;

public class LocationMapper {
    public static LocationDTO toDTO(Location l) {
        if (l == null) return null;
        LocationDTO dto = new LocationDTO();
        dto.id = l.getId();
        dto.x = l.getX();
        dto.y = l.getY();
        dto.z = l.getZ();
        return dto;
    }

    public static Location fromDTO(LocationDTO dto) {
        if (dto == null) return null;
        Location l = new Location();
        l.setId(dto.id);
        l.setX(dto.x);
        l.setY(dto.y);
        l.setZ(dto.z);
        return l;
    }
}
