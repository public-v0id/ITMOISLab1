package ru.se.ifmo.is.lab1.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ru.se.ifmo.is.lab1.beans.Coordinates;

import java.util.List;

@ApplicationScoped
@Transactional
public class CoordinatesRepository {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    public void save(Coordinates coordinates) {
        em.persist(coordinates);
    }

    public Coordinates findById(Long id) {
        return em.find(Coordinates.class, id);
    }

    public List<Coordinates> findAll() {
        return em.createQuery("SELECT c FROM coordinates c", Coordinates.class)
                .getResultList();
    }

    public Coordinates update(Coordinates coordinates) {
        return em.merge(coordinates);
    }

    public void delete(Long id) {
        Coordinates c = em.find(Coordinates.class, id);
        if (c != null) {
            em.remove(c);
        }
    }

    public Coordinates findByValues(Double x, Integer y) {
        return em.createQuery(
                        "SELECT c FROM coordinates c WHERE c.x = :x AND c.y = :y",
                        Coordinates.class
                )
                .setParameter("x", x)
                .setParameter("y", y)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}