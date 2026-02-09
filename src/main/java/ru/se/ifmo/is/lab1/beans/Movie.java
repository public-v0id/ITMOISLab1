package ru.se.ifmo.is.lab1.beans;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity(name = "movie")
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Valid
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Min(value = 1)
    @Column(name = "oscars_count", nullable = false)
    private Integer oscarsCount;

    @NotNull
    @Min(value = 1)
    @Column(name = "budget", nullable = false)
    private Integer budget;

    @NotNull
    @Min(value = 1)
    @Column(name = "total_box_office", nullable = false)
    private Integer totalBoxOffice;

    @Enumerated(EnumType.STRING)
    @Column(name = "mpaa_rating")
    private MpaaRating mpaaRating;

    @NotNull
    @Valid
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "director_id", nullable = false)
    private Person director;

    @Valid
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "screenwriter_id")
    private Person screenwriter;

    @NotNull
    @Valid
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "operator_id", nullable = false)
    private Person operator;

    @NotNull
    @Min(value = 1)
    @Column(name = "length", nullable = false)
    private Integer length;

    @NotNull
    @Min(value = 1)
    @Column(name = "golden_palm_count", nullable = false)
    private Integer goldenPalmCount;

    @NotNull
    @Min(value = 1)
    @Column(name = "usa_box_office", nullable = false)
    private Integer usaBoxOffice;

    @NotNull
    @NotBlank
    @Column(name = "tagline", nullable = false)
    private String tagline;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private MovieGenre genre;

    public Movie() {
    }

    public Movie(String name, Coordinates coordinates, Integer oscarsCount,
                 Integer budget, Integer totalBoxOffice, MpaaRating mpaaRating,
                 Person director, Person screenwriter, Person operator,
                 Integer length, Integer goldenPalmCount, Integer usaBoxOffice,
                 String tagline, MovieGenre genre) {
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.budget = budget;
        this.totalBoxOffice = totalBoxOffice;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.screenwriter = screenwriter;
        this.operator = operator;
        this.length = length;
        this.goldenPalmCount = goldenPalmCount;
        this.usaBoxOffice = usaBoxOffice;
        this.tagline = tagline;
        this.genre = genre;
    }

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public void setTotalBoxOffice(Integer totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }

    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getGoldenPalmCount() {
        return goldenPalmCount;
    }

    public void setGoldenPalmCount(Integer goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }

    public Integer getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public void setUsaBoxOffice(Integer usaBoxOffice) {
        this.usaBoxOffice = usaBoxOffice;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", oscarsCount=" + oscarsCount +
                ", budget=" + budget +
                ", totalBoxOffice=" + totalBoxOffice +
                ", mpaaRating=" + mpaaRating +
                ", director=" + director +
                ", screenwriter=" + screenwriter +
                ", operator=" + operator +
                ", length=" + length +
                ", goldenPalmCount=" + goldenPalmCount +
                ", usaBoxOffice=" + usaBoxOffice +
                ", tagline='" + tagline + '\'' +
                ", genre=" + genre +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (name != null ? !name.equals(movie.name) : movie.name != null) return false;
        if (coordinates != null ? !coordinates.equals(movie.coordinates) : movie.coordinates != null) return false;
        if (creationDate != null ? !creationDate.equals(movie.creationDate) : movie.creationDate != null) return false;
        if (oscarsCount != null ? !oscarsCount.equals(movie.oscarsCount) : movie.oscarsCount != null) return false;
        if (budget != null ? !budget.equals(movie.budget) : movie.budget != null) return false;
        if (totalBoxOffice != null ? !totalBoxOffice.equals(movie.totalBoxOffice) : movie.totalBoxOffice != null)
            return false;
        if (mpaaRating != movie.mpaaRating) return false;
        if (director != null ? !director.equals(movie.director) : movie.director != null) return false;
        if (screenwriter != null ? !screenwriter.equals(movie.screenwriter) : movie.screenwriter != null) return false;
        if (operator != null ? !operator.equals(movie.operator) : movie.operator != null) return false;
        if (length != null ? !length.equals(movie.length) : movie.length != null) return false;
        if (goldenPalmCount != null ? !goldenPalmCount.equals(movie.goldenPalmCount) : movie.goldenPalmCount != null)
            return false;
        if (usaBoxOffice != null ? !usaBoxOffice.equals(movie.usaBoxOffice) : movie.usaBoxOffice != null) return false;
        if (tagline != null ? !tagline.equals(movie.tagline) : movie.tagline != null) return false;
        return genre == movie.genre;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (oscarsCount != null ? oscarsCount.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (totalBoxOffice != null ? totalBoxOffice.hashCode() : 0);
        result = 31 * result + (mpaaRating != null ? mpaaRating.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (screenwriter != null ? screenwriter.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (goldenPalmCount != null ? goldenPalmCount.hashCode() : 0);
        result = 31 * result + (usaBoxOffice != null ? usaBoxOffice.hashCode() : 0);
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
}