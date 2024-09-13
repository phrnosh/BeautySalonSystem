package com.beautysalon.beautysalonsystem.model.tools;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcProvider {
    @Getter
    private static JdbcProvider jpa = new JdbcProvider();
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("beautysalon");

    private JdbcProvider() {
    }

    public EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

}
