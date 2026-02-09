package ru.se.ifmo.is.lab1.mapper;

import ru.se.ifmo.is.lab1.beans.Color;
import ru.se.ifmo.is.lab1.beans.Location;
import ru.se.ifmo.is.lab1.beans.Person;
import ru.se.ifmo.is.lab1.dto.PersonDTO;
import ru.se.ifmo.is.lab1.repository.LocationRepository;

public class PersonMapper {
    public static PersonDTO toDTO(Person p) {
        if (p == null) return null;
        PersonDTO dto = new PersonDTO();
        dto.id = p.getId();
        dto.eyeColor = p.getEyeColor();
        dto.hairColor = p.getHairColor();
        dto.name = p.getName();
        dto.locationID = p.getLocation().getId();
        dto.passportID = p.getPassportID();
        dto.nationality = p.getNationality();
        return dto;
    }

    public static Person fromDTO(PersonDTO dto, Location location) {
        if (dto == null) return null;
        Person p = new Person();
        p.setId(dto.id);
        p.setEyeColor(dto.eyeColor);
        p.setHairColor(dto.hairColor);
        p.setName(dto.name);
        p.setLocation(location);
        p.setPassportID(dto.passportID);
        p.setNationality(dto.nationality);
        return p;
    }
}
