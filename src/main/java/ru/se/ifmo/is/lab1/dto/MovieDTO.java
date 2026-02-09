package ru.se.ifmo.is.lab1.dto;

import ru.se.ifmo.is.lab1.beans.*;
import java.time.LocalDateTime;

public class MovieDTO {
    public Long id;
    public String name;
    public Long coordinates;
    public LocalDateTime creationDate;
    public int oscarsCount;
    public Integer budget;
    public Integer totalBoxOffice;
    public MpaaRating mpaaRating;
    public Long director;
    public Long screenwriter;
    public Long operator;
    public Integer length;
    public int goldenPalmCount;
    public int usaBoxOffice;
    public String tagline;
    public MovieGenre genre;
}