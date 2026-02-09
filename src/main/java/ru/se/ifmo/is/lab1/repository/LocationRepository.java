package ru.se.ifmo.is.lab1.repository;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ru.se.ifmo.is.lab1.beans.Location;

import java.util.List;

@ApplicationScoped
@Transactional
public class LocationRepository {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    public void save(Location location) {
        em.persist(location);
    }

    public Location findById(Long id) {
        return em.find(Location.class, id);
    }

    public List<Location> findAll() {
        return em.createQuery("SELECT l FROM location l", Location.class)
                .getResultList();
    }

    public Location update(Location location) {
        return em.merge(location);
    }

    public void delete(Long id) {
        Location l = em.find(Location.class, id);
        if (l != null) {
            em.remove(l);
        }
    }

    public Location findByValues(Integer x, Double y, float z) {
        return em.createQuery(
                        "SELECT l FROM location l WHERE l.x = :x AND l.y = :y AND l.z = :z",
                        Location.class
                )
                .setParameter("x", x)
                .setParameter("y", y)
                .setParameter("z", z)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}