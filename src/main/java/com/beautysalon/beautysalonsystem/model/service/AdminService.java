package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Admin;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Singleton
public class AdminService implements Serializable {

    @PersistenceContext(unitName = "beautySalon")
    private EntityManager entityManager;


    public Admin save(Admin admin) throws Exception {
        entityManager.persist(admin);
        return admin;
    }

    public Admin edit(Admin admin) throws Exception {
        Admin foundAdmin = entityManager.find(Admin.class, admin.getId());
        if (foundAdmin != null) {
            entityManager.merge(admin);
        }
        return admin;
    }

    public Admin remove(Long id) throws Exception {
        Admin admin = entityManager.find(Admin.class,id );
        if (admin != null) {
            admin.setDeleted(true);
            entityManager.merge(admin);
        }
        return admin;
    }

    public List<Admin> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from adminEntity oo where oo.deleted=false", Admin.class)
                .getResultList();
    }
    public Admin findById(Long id) throws Exception {
        return entityManager.find(Admin.class, id);
    }
//    hh

    public List<Admin> findByUsername(String username) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity a where a.user.username=:username", Admin.class)
                .setParameter("username", username )
                .getResultList();
    }

    public List<Admin> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity  a where a.name like :name and a.family like :family", Admin.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    public List<Admin> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity a where a.user.username=:username and a.user.password=:password", Admin.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    public List<Admin> findByEmail(String email) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity a where a.email like :email", Admin.class)
                .setParameter("email", email )
                .getResultList();
    }

    public Admin findByPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity a where a.phoneNumber =:phoneNumber", Admin.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }
    public Admin findByNationalCode(String nationalCode) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity a where a.nationalCode=:nationalCode", Admin.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
    }
}