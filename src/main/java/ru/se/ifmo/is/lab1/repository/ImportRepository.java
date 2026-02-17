package ru.se.ifmo.is.lab1.repository;


import ru.se.ifmo.is.lab1.beans.ImportOperation;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ImportRepository {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager entityManager;

    public void save(ImportOperation op) {
        entityManager.persist(op);
    }

    public List<ImportOperation> findAll() {
        return entityManager
                .createQuery("FROM ImportOperation", ImportOperation.class)
                .getResultList();
    }

    public List<ImportOperation> findByUser(String username) {
        return entityManager
                .createQuery("FROM ImportOperation WHERE username = :u", ImportOperation.class)
                .setParameter("u", username)
                .getResultList();
    }
}