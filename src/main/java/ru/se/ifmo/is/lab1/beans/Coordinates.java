package ru.se.ifmo.is.lab1.beans;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity(name="coordinates")
@Table(name="coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Max(value = 812)
    @Column(nullable = false)
    private Double x;

    @NotNull
    @Max(value = 944)
    @Column(nullable = false)
    private Integer y;

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getX() {
        return x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getY() {
        return y;
    }
}
