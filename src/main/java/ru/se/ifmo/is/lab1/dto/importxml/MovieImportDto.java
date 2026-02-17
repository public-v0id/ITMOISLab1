package ru.se.ifmo.is.lab1.dto.importxml;

import ru.se.ifmo.is.lab1.beans.MovieGenre;
import ru.se.ifmo.is.lab1.beans.MpaaRating;
import ru.se.ifmo.is.lab1.dto.CoordinatesDTO;
import ru.se.ifmo.is.lab1.dto.PersonDTO;
import ru.se.ifmo.is.lab1.repository.CoordinatesRepository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MovieImportDto {

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private CoordinatesDto coordinates;

    @XmlElement(required = true)
    private int oscarsCount;

    @XmlElement(required = true)
    private Integer budget;

    @XmlElement(required = true)
    private Integer totalBoxOffice;

    private MpaaRating mpaaRating;

    @XmlElement(required = true)
    private PersonDto director;

    private PersonDto screenwriter;

    @XmlElement(required = true)
    private PersonDto operator;

    @XmlElement(required = true)
    private Integer length;

    @XmlElement(required = true)
    private int goldenPalmCount;

    @XmlElement(required = true)
    private int usaBoxOffice;

    @XmlElement(required = true)
    private String tagline;

    private MovieGenre genre;

    public MovieGenre getGenre() {
        return genre;
    }

    public Integer getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Integer getBudget() {
        return budget;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public String getName() {
        return name;
    }

    public int getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public CoordinatesDto getCoordinates() {
        return coordinates;
    }

    public PersonDto getDirector() {
        return director;
    }

    public PersonDto getOperator() {
        return operator;
    }

    public PersonDto getScreenwriter() {
        return screenwriter;
    }

    public int getGoldenPalmCount() {
        return goldenPalmCount;
    }

    public Integer getLength() {
        return length;
    }

    public String getTagline() {
        return tagline;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }
}

