package ru.se.ifmo.is.lab1.dto.importxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "movies")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieImportList {

    @XmlElement(name = "movie")
    private List<MovieImportDto> movies;

    public List<MovieImportDto> getMovies() {
        return movies;
    }
}
