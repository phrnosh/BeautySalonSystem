package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class AddressService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Address save(Address address) throws Exception {
        entityManager.persist(address);
        return address;
    }

    @Transactional
    public Address edit(Address address) throws Exception {
        Address foundAddress = entityManager.find(Address.class, address.getId());
        if (foundAddress != null) {
            entityManager.merge(address);
        }
        return address;
    }

    @Transactional
    public Address remove(Long id) throws Exception {
        Address address = entityManager.find(Address.class,id );
        if (address != null) {
            address.setDeleted(true);
            entityManager.merge(address);
        }
        return address;
    }

    @Transactional
    public List<Address> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from addressEntity oo where oo.deleted=false", Address.class)
                .getResultList();
    }

    @Transactional
    public Address findById(Long id) throws Exception {
        return entityManager.find(Address.class, id);
    }

    @Transactional
    public Address findByPostalCode(String postalCode) throws Exception {
        return entityManager
                .createQuery("select ad from addressEntity ad where ad.postalCode=:postalCode", Address.class)
                .setParameter("postalCode", postalCode)
                .getSingleResult();
    }

    @Transactional
    public List<Address> findByCountryAndState(String countryName, String stateName) throws Exception {
        return entityManager
                .createQuery("select ad from addressEntity ad where ad.countryName like :countryName and ad.stateName like :stateName", Address.class)
                .setParameter("countryName", countryName + "%")
                .setParameter("stateName", stateName + "%")
                .getResultList();
    }

    @Transactional
    public List<Address> findByCity (String cityName) throws Exception{
        return entityManager
                .createQuery("select ad from addressEntity ad where ad.cityName like :cityName", Address.class)
                .setParameter("cityName", cityName + "%")
                .getResultList();
    }

    @Transactional
    public List<Address> findByText (String text) throws  Exception{
        return entityManager
                .createQuery("select ad from addressEntity ad where ad.countryName like :countryName and ad.stateName like :stateName OR" +
                        " ad.cityName LIKE: cityName OR ad.villageName LIKE: villageName OR ad.regionName LIKE: regionName OR" +
                        " ad.streetName LIKE: streetName OR ad.platesNumber LIKE: platesNumber OR ad.floorNumber LIKE: floorNumber OR" +
                        " ad.homeUnit LIKE: homeUnit" , Address.class)

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
