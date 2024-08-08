package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Stylist;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class StylistService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Stylist save(Stylist stylist) throws Exception {
        entityManager.persist(stylist);
        return stylist;
    }

    @Transactional
    public Stylist edit(Stylist stylist) throws Exception {
        Stylist foundStylist = entityManager.find(Stylist.class, stylist.getId());
        if (foundStylist != null) {
            entityManager.merge(stylist);
        }
        return stylist;
    }

    @Transactional
    public Stylist remove(Long id) throws Exception {
        Stylist stylist=entityManager.find(Stylist.class, id);
        if(stylist!=null){
            stylist.setDeleted(true);
            entityManager.merge(stylist);
        }
        return stylist;
    }

    @Transactional
    public List<Stylist> findAll() throws Exception {
        return entityManager
                .createQuery("select n from stylistEntity n where n .deleted=false", Stylist.class)
                .getResultList();
    }

    @Transactional
    public Stylist findById(Long id) throws Exception {
        return entityManager.find(Stylist.class,id);
    }

    @Transactional
    public List<Stylist> findByUsername(String username) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.user.username=:username", Stylist.class)
                .setParameter("username", username )
                .getResultList();
    }

    @Transactional
    public List<Stylist> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.name like :name and s.family like :family", Stylist.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public List<Stylist> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.user.username=:username and s.user.password=:password", Stylist.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional
    public List<Stylist> findByEmail(String email) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.email like :email",Stylist.class)
                .setParameter("email",email)
                .getResultList();
    }

    @Transactional
    public Stylist findByPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.phoneNumber =:phoneNumber", Stylist.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }

    @Transactional
    public Stylist fideByNationalCode(String nationalCode) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.nationalCode=:nationalCode", Stylist.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
    }

    @Transactional
    public Stylist fideByCareer(String career) throws Exception {
        return entityManager
                .createQuery("select s from stylistEntity s where s.career=:career", Stylist.class)
                .setParameter("career", career)
                .getSingleResult();
    }

    @Transactional
    public List<Stylist> findByAddress (String text) throws  Exception{
        return entityManager
                .createQuery("select s from stylistEntity s where s.address.countryName like :countryName and s.address.stateName like :stateName OR" +
                        " s.address.cityName LIKE: cityName OR s.address.villageName LIKE: villageName OR s.address.regionName LIKE: regionName OR" +
                        " s.address.streetName LIKE: streetName OR s.address.platesNumber LIKE: platesNumber OR s.address.floorNumber LIKE: floorNumber OR" +
                        " s.address.homeUnit LIKE: homeUnit" , Stylist.class)

                .setParameter("countryName", text + "%")
                .setParameter("stateName", text + "%")
                .setParameter("cityName", text + "%")
                .setParameter("villageName", text + "%")
                .setParameter("regionName", text + "%")
                .setParameter("streetName", text + "%")
                .setParameter("platesNumber", text + "%")
                .setParameter("floorNumber", text + "%")
                .setParameter("homeUnit", text + "%")
                .getResultList();
    }
}