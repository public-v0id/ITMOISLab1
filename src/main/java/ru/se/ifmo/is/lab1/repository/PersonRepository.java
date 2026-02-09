package ru.se.ifmo.is.lab1.repository;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import ru.se.ifmo.is.lab1.beans.MovieGenre;
import ru.se.ifmo.is.lab1.beans.Person;

import java.util.List;

@ApplicationScoped
@Transactional
public class PersonRepository {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    public void save(Person person) {
        em.persist(person);
    }

    public Person update(Person person) {
        return em.merge(person);
    }

    public void delete(Long id) {
        Person person = em.find(Person.class, id);
        if (person != null) {
            em.remove(person);
        }
    }

    public Person findById(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(Person.class, id);
    }

    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM person p", Person.class)
                .getResultList();
    }

    public Person findByPassportID(String passportID) {
        TypedQuery<Person> query = em.createQuery(
                "SELECT p FROM person p WHERE p.passportID = :pid", Person.class);
        query.setParameter("pid", passportID);
        return query.getResultStream().findFirst().orElse(null);
    }

    public boolean existsByPassportID(String passportID) {
        Long count = em.createQuery(
                        "SELECT COUNT(p) FROM person p WHERE p.passportID = :pid", Long.class)
                .setParameter("pid", passportID)
                .getSingleResult();
        return count > 0;
    }

    public List<Person> findDirectorsWithoutOscars() {
        return em.createQuery("SELECT DISTINCT p " +
                        "FROM person p " +
                        "WHERE p IN (" +
                        "SELECT m.director " +
                        "FROM movie m " +
                        "GROUP BY m.director " +
                        "HAVING SUM(m.oscarsCount) = 0" +
                        ")", Person.class)
                .getResultList();
    }

    public List<Person> findDirectorsWithGenre(MovieGenre genre) {
        return em.createQuery("SELECT DISTINCT m.director " +
                        "FROM movie m " +
                        "WHERE m.genre = :genre", Person.class)
                .setParameter("genre", genre)
                .getResultList();
    }

    public int removeOscarsFromDirectorsWithGenre(MovieGenre genre) {
        return em.createQuery("UPDATE movie m SET m.oscarsCount = 0 " +
                        "WHERE m.director IN (" +
                        "SELECT DISTINCT m2.director " +
                        "FROM movie m2 " +
                        "WHERE m2.genre = :genre" +
                        ")")
                .setParameter("genre", genre)
                .executeUpdate();
    }
}