package com.beautysalon.beautysalonsystem.model.service;


import com.beautysalon.beautysalonsystem.model.entity.Booking;
import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Services;
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
        return entityManager.find(Manager.class, id);
    }

    @Transactional
    public Manager findByUsername(String username) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.user.username =:username and m.deleted=false ", Manager.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
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
    public Manager findByNationalCode(String nationalCode) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.nationalCode =:nationalCode and m.deleted=false ", Manager.class)
                        .setParameter("nationalCode", nationalCode)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Manager> findByFamily(String family) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.family like :family and m.deleted=false ", Manager.class)
                .setParameter("family", family.toUpperCase() + "%")
                .getResultList();
    }

    @Transactional
    public Salon findSalonByManagerId(Long managerId) throws Exception {
        List<Salon> salonList =
                entityManager
                        .createQuery("select m.salon from managerEntity m where m.id =:managerId and m.deleted=false ", Salon.class)
                        .setParameter("managerId", managerId)
                        .getResultList();
        if (!salonList.isEmpty()) {
            return salonList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Booking findBookingByManagerId(Long managerId) throws Exception {
        List<Booking> bookingList =
                entityManager
                        .createQuery("select m.bookingList from managerEntity m where m.id =:managerId and m.deleted=false ", Booking.class)
                        .setParameter("managerId", managerId)
                        .getResultList();
        if (!bookingList.isEmpty()) {
            return bookingList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Manager findManagerBySalonId(Long salonId) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.salon.id =:salonId and m.deleted=false ", Manager.class)
                        .setParameter("salonId", salonId)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Services> findServicesByManagerId(Long managerId) throws Exception {
        return entityManager
                .createQuery("select m.salon.servicesList from managerEntity m where m.id =:managerId and m.deleted=false ", Services.class)
                .setParameter("managerId", managerId)
                .getResultList();
    }

    @Transactional
    public List<Manager> findManagersWantingSalon() throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.salon =null and m.deleted=false ", Manager.class)
                .getResultList();
    }
}
