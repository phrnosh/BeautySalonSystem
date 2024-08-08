package com.beautysalon.beautysalonsystem.model.service;


import com.beautysalon.beautysalonsystem.model.entity.Manager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ManagerService implements Serializable {
    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Manager save(Manager manager) throws Exception {
        entityManager.persist(manager);
        return manager;
    }

    @Transactional
    public Manager edit(Manager manager) throws Exception {
        Manager foundManager = entityManager.find(Manager.class, manager.getId());
        if (foundManager != null) {
            entityManager.merge(manager);
        }
        return manager;
    }

    @Transactional
    public Manager remove(Long id) throws Exception {
        Manager manager=entityManager.find(Manager.class, id);
        if(manager!=null){
            manager.setDeleted(true);
            entityManager.merge(manager);
        }
        return manager;
    }

    @Transactional
    public List<Manager> findAll() throws Exception {
        return entityManager
                .createQuery("select n from managerEntity n where n .deleted=false", Manager.class)
                .getResultList();
    }

    @Transactional
    public Manager findById(Long id) throws Exception {
        return entityManager.find(Manager.class,id);
    }

    @Transactional
    public List<Manager> findByUsername(String username) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.user.username=:username", Manager.class)
                .setParameter("username", username )
                .getResultList();
    }

    @Transactional
    public List<Manager> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.name like :name and m.family like :family", Manager.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public List<Manager> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.user.username=:username and m.user.password=:password", Manager.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional
    public List<Manager> findByEmail(String email) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.email like :email",Manager.class)
                .setParameter("email",email)
                .getResultList();
    }

    @Transactional
    public Manager findByPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.phoneNumber =:phoneNumber", Manager.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }

    @Transactional
    public Manager fideByNationalCode(String nationalCode) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.nationalCode=:nationalCode", Manager.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
    }
}
