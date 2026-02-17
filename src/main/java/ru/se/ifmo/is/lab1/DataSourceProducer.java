package ru.se.ifmo.is.lab1;

import org.apache.commons.dbcp2.managed.BasicManagedDataSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

@Singleton
@Startup
public class DataSourceProducer {
    private BasicManagedDataSource dataSource;

    @PostConstruct
    public void init() {
        dataSource = new BasicManagedDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/movies");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(5);
        dataSource.setMaxWaitMillis(5000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        try {
            TransactionManager tm = (TransactionManager) new InitialContext()
                    .lookup("java:jboss/TransactionManager");
            dataSource.setTransactionManager(tm);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup TransactionManager", e);
        }
        try {
            new InitialContext().bind("java:comp/env/jdbc/moviesDS", dataSource);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to bind DataSource to JNDI", e);
        }
    }

    @PreDestroy
    public void destroy() {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
