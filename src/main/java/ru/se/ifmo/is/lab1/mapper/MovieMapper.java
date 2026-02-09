package ru.se.ifmo.is.lab1.mapper;

import ru.se.ifmo.is.lab1.beans.Coordinates;
import ru.se.ifmo.is.lab1.beans.Movie;
import ru.se.ifmo.is.lab1.beans.Person;
import ru.se.ifmo.is.lab1.dto.MovieDTO;

public class MovieMapper {
    public static MovieDTO toDTO(Movie m) {
        if (m == null) return null;
        MovieDTO dto = new MovieDTO();
        dto.id = m.getId();
        dto.name = m.getName();
        dto.coordinates = m.getCoordinates().getId();
        dto.creationDate = m.getCreationDate();
        dto.oscarsCount = m.getOscarsCount();
        dto.budget = m.getBudget();
        dto.totalBoxOffice = m.getTotalBoxOffice();
        dto.mpaaRating = m.getMpaaRating();
        dto.director = m.getDirector().getId();
        if (m.getScreenwriter() != null) {
            dto.screenwriter = m.getScreenwriter().getId();
        }
        dto.operator = m.getOperator().getId();
        dto.length = m.getLength();
        dto.goldenPalmCount = m.getGoldenPalmCount();
        dto.usaBoxOffice = m.getUsaBoxOffice();
        dto.tagline = m.getTagline();
        dto.genre = m.getGenre();
        return dto;
    }

    public static Movie fromDTO(MovieDTO dto, Person director, Person screenwriter, Person operator, Coordinates coordinates) {
        if (dto == null) return null;
        Movie m = new Movie();
        m.setId(dto.id);
        m.setName(dto.name);
        m.setCreationDate(dto.creationDate);
        m.setCoordinates(coordinates);
        m.setOscarsCount(dto.oscarsCount);
        m.setBudget(dto.budget);
        m.setTotalBoxOffice(dto.totalBoxOffice);
        m.setMpaaRating(dto.mpaaRating);
        m.setDirector(director);
        m.setScreenwriter(screenwriter);
        m.setOperator(operator);
        m.setLength(dto.length);
        m.setGoldenPalmCount(dto.goldenPalmCount);
        m.setUsaBoxOffice(dto.usaBoxOffice);
        m.setTagline(dto.tagline);
        m.setGenre(dto.genre);
        return m;
    }
}
