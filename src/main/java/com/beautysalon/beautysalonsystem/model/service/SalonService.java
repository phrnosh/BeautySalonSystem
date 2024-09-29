package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Services;
import com.beautysalon.beautysalonsystem.model.entity.Timing;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class SalonService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Salon save(Salon salon) throws Exception {
        entityManager.persist(salon);
        return salon;
    }

    @Transactional
    public Salon edit(Salon salon) throws Exception {
        Salon foundSalon = entityManager.find(Salon.class, salon.getId());
        if (foundSalon != null) {
            entityManager.merge(salon);

        }
        return salon;
    }

    @Transactional
    public Salon remove(Long id) throws Exception {
        Salon salon = entityManager.find(Salon.class, id);
        if (salon != null) {
            for (Timing timing : salon.getTimingList()) {
                timing.setDeleted(true);
                entityManager.merge(timing);
            }

            for (Services services : salon.getServicesList()) {
                services.setDeleted(true);
                entityManager.merge(services);
            }
            salon.setDeleted(true);
            entityManager.merge(salon);
        }
        return salon;
    }

    @Transactional
    public List<Salon> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from salonEntity oo where oo.deleted=false order by id", Salon.class)
                .getResultList();
    }

    @Transactional
    public Salon findById(Long id) throws Exception {
        return entityManager.find(Salon.class, id);
    }

    @Transactional
    public Salon findByName(String name) throws Exception {
        List<Salon> salonList = entityManager
                .createQuery("select sa from salonEntity sa where sa.name like :name and deleted=false ", Salon.class)
                .setParameter("name", name.toUpperCase())
                .getResultList();
        if (!salonList.isEmpty()) {
            return salonList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Salon findByAddress(String address) throws Exception {
        List<Salon> salonList = entityManager
                .createQuery("select sa from salonEntity sa where sa.address like :address and sa.deleted=false ", Salon.class)
                .setParameter("address", address)
                .getResultList();
        if (!salonList.isEmpty()) {
            return salonList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Salon findSalonByServicesId(Long servicesId) {
        List<Salon> salonList = entityManager
                .createQuery("select sa from salonEntity sa join sa.servicesList s where s.id = :servicesId", Salon.class)
                .setParameter("servicesId", servicesId)
                .getResultList();
        if (!salonList.isEmpty()) {
            return salonList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Services> findSalonActiveServices(Long salonId) {
        return entityManager
                .createQuery("select s from salonEntity sa join sa.servicesList s where sa.id = :salonId and s.status = true", Services.class)
                .setParameter("salonId", salonId)
                .getResultList();

    }
}
