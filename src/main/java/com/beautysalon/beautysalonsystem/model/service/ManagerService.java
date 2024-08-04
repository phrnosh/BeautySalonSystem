package com.beautysalon.beautysalonsystem.model.service;


import com.beautysalon.beautysalonsystem.model.entity.Manager;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Singleton
public class ManagerService implements Serializable {
    @PersistenceContext(unitName = "beautySalon")
    private EntityManager entityManager;


    public Manager save(Manager manager) throws Exception {
        entityManager.persist(manager);
        return manager;
    }

    public Manager edit(Manager manager) throws Exception {
        Manager foundManager = entityManager.find(Manager.class, manager.getId());
        if (foundManager != null) {
            entityManager.merge(manager);
        }
        return manager;
    }


    public Manager remove(Long id) throws Exception {
        Manager manager=entityManager.find(Manager.class, id);
        if(manager!=null){
            manager.setDeleted(true);
            entityManager.merge(manager);
        }
        return manager;
    }

    public List<Manager> findAll() throws Exception {
        return entityManager
                .createQuery("select n from managerEntity n where n .deleted=false", Manager.class)
                .getResultList();
    }
    public Manager findById(Long id) throws Exception {
        return entityManager.find(Manager.class,id);
    }

    public List<Manager> findByUsername(String username) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.user.username=:username", Manager.class)
                .setParameter("username", username )
                .getResultList();
    }

    public List<Manager> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.name like :name and m.family like :family", Manager.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    public List<Manager> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.user.username=:username and m.user.password=:password", Manager.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    public List<Manager> findByEmail(String email) throws Exception {
        return entityManager
                .createQuery("select o from managerEntity o where o.email like :email",Manager.class)
                .setParameter("email",email)
                .getResultList();
    }

    public Manager findByPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select o from managerEntity o where o.phoneNumber =:phoneNumber", Manager.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }

    public Manager fideByNationalCode(String nationalCode) throws Exception {
        return entityManager
                .createQuery("select o from managerEntity o where o.nationalCode=:nationalCode", Manager.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
    }
}
