package ru.se.ifmo.is.lab1.repository;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import ru.se.ifmo.is.lab1.beans.*;

import java.util.List;

@ApplicationScoped
@Transactional
public class MovieRepository {
    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    public Movie find(Long id) {
        return em.find(Movie.class, id);
    }

    public List<Movie> findAll() {
        StringBuilder q = new StringBuilder("SELECT m FROM movie m");
        TypedQuery<Movie> query = em.createQuery(q.toString(), Movie.class);
        return query.getResultList();
    }

    public List<Movie> findAll(int offset, int limit, String sortBy, boolean asc, String filterColumn, String filterValue) {
        StringBuilder q = new StringBuilder("SELECT m FROM movie m");
        if (filterColumn != null && filterValue != null) {
            q.append(" WHERE m.").append(filterColumn).append(" = :filterValue");
        }
        if (sortBy != null) {
            q.append(" ORDER BY m.").append(sortBy).append(asc ? " ASC" : " DESC");
        }
        TypedQuery<Movie> query = em.createQuery(q.toString(), Movie.class);
        if (filterColumn != null && filterValue != null) {
            query.setParameter("filterValue", filterValue);
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public Movie save(Movie m) {
        if (m.getCoordinates() != null && m.getCoordinates().getId() != null) {
            Coordinates managedCoordinates = em.find(Coordinates.class, m.getCoordinates().getId());
            m.setCoordinates(managedCoordinates);
        }

        if (m.getDirector() != null && m.getDirector().getId() != null) {
            Person managedDirector = em.find(Person.class, m.getDirector().getId());
            m.setDirector(managedDirector);
        }

        if (m.getScreenwriter() != null && m.getScreenwriter().getId() != null) {
            Person managedScreenwriter = em.find(Person.class, m.getScreenwriter().getId());
            m.setScreenwriter(managedScreenwriter);
        }

        if (m.getOperator() != null && m.getOperator().getId() != null) {
            Person managedOperator = em.find(Person.class, m.getOperator().getId());
            m.setOperator(managedOperator);
        }

        if (m.getId() == null) {
            em.persist(m);
            return m;
        } else {
            return em.merge(m);
        }
    }

    public Movie update(Movie movie) {
        Movie existing = em.find(Movie.class, movie.getId());
        if (existing == null) {
            return null;
        }

        if (movie.getCoordinates() != null) {
            if (movie.getCoordinates().getId() != null) {
                Coordinates managedCoordinates = em.find(Coordinates.class, movie.getCoordinates().getId());
                existing.setCoordinates(managedCoordinates);
            } else {
                existing.setCoordinates(movie.getCoordinates());
            }
        }

        if (movie.getDirector() != null && movie.getDirector().getId() != null) {
            Person managedDirector = em.find(Person.class, movie.getDirector().getId());
            existing.setDirector(managedDirector);
        } else {
            existing.setDirector(movie.getDirector());
        }

        if (movie.getScreenwriter() != null && movie.getScreenwriter().getId() != null) {
            Person managedScreenwriter = em.find(Person.class, movie.getScreenwriter().getId());
            existing.setScreenwriter(managedScreenwriter);
        } else {
            existing.setScreenwriter(movie.getScreenwriter());
        }

        if (movie.getOperator() != null && movie.getOperator().getId() != null) {
            Person managedOperator = em.find(Person.class, movie.getOperator().getId());
            existing.setOperator(managedOperator);
        } else {
            existing.setOperator(movie.getOperator());
        }

        existing.setName(movie.getName());
        existing.setOscarsCount(movie.getOscarsCount());
        existing.setBudget(movie.getBudget());
        existing.setTotalBoxOffice(movie.getTotalBoxOffice());
        existing.setMpaaRating(movie.getMpaaRating());
        existing.setLength(movie.getLength());
        existing.setGoldenPalmCount(movie.getGoldenPalmCount());
        existing.setUsaBoxOffice(movie.getUsaBoxOffice());
        existing.setTagline(movie.getTagline());
        existing.setGenre(movie.getGenre());

        return existing;
    }

    public void delete(Movie m) {
        Movie attached = em.contains(m) ? m : em.merge(m);
        em.remove(attached);
    }

    public void deleteById(long id) {
        try {
            Movie movie = this.find(id);
            if (movie != null) {
                em.remove(movie);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting movie with id: " + id, e);
        }
    }

    public List<Movie> findByNameStartsWith(String prefix) {
        return em.createQuery("SELECT m FROM movie m WHERE m.name LIKE :p", Movie.class)
                .setParameter("p", prefix + "%")
                .getResultList();
    }

    public List<Movie> findByGenreLessThan(MovieGenre genre) {
        return em.createQuery("SELECT m FROM movie m WHERE m.genre IS NOT NULL AND m.genre < :g", Movie.class)
                .setParameter("g", genre)
                .getResultList();
    }

    public List<Integer> uniqueUsaBoxOffice() {
        return em.createQuery("SELECT DISTINCT m.usaBoxOffice FROM movie m", Integer.class)
                .getResultList();
    }
}