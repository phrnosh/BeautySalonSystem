package com.beautysalon.beautysalonsystem.model.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

public class JpaProvider {
    @Getter
    private static JpaProvider jpa = new JpaProvider();
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("beautysalon");

    private JpaProvider() {
    }

    public EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}