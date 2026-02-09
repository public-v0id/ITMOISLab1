package ru.se.ifmo.is.lab1.beans;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity(name="person")
@Table(name="person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "eye_color")
    private Color eyeColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color", nullable = false)
    private Color hairColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @NotBlank
    @Column(name = "passport_id", nullable = false, unique = true)
    private String passportID;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality")
    private Country nationality;

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public String getPassportID() {
        return passportID;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}