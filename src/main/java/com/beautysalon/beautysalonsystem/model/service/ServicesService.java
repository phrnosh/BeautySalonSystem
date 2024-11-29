package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Services;
import com.beautysalon.beautysalonsystem.model.entity.Timing;
import com.beautysalon.beautysalonsystem.model.entity.enums.ServicesType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ServicesService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Services save(Services services) throws Exception {
        entityManager.persist(services);
        return services;
    }

    @Transactional
    public Services edit(Services services) throws Exception {
        Services foundServices = entityManager.find(Services.class, services.getId());
        if (foundServices != null) {
            entityManager.merge(services);
        }
        return services;
    }

    @Transactional
    public Services remove(Long id) throws Exception {
        Services services=entityManager.find(Services.class, id);
        if(services!=null){
            services.setDeleted(true);
            entityManager.merge(services);
        }
        return services;
    }

    @Transactional
    public List<Services> findAll() throws Exception {
        return entityManager
                .createQuery("select s from servicesEntity s where s .deleted=false", Services.class)
                .getResultList();
    }

    @Transactional
    public Services findById(Long id) throws Exception {
        return entityManager.find(Services.class,id);
    }

    @Transactional
    public List<Services> findByStylist(String stylistName) throws Exception {
        return entityManager
                .createQuery("select s from servicesEntity s where s.stylistName like :stylist_name", Services.class)
                .setParameter("stylist_name", stylistName + "%")
                .getResultList();
    }

//    @Transactional
//    public List<Services> findByStylist(String name, String family) throws Exception {
//        return entityManager
//                .createQuery("select m from servicesEntity m where m.stylist.name like :name and m.stylist.family like :family", Services.class)
//                .setParameter("name", name + "%")
//                .setParameter("family", family + "%")
//                .getResultList();
//    }

    @Transactional
    public List<Services> findUsableServices() throws Exception {
        return entityManager
            .createQuery("select s from servicesEntity s where s.status=true and s.deleted=false ", Services.class)
            .getResultList();
}

    @Transactional
    public List<Services> findByText(String text) throws Exception {
        return entityManager
                .createQuery("select s from servicesEntity s where " +
                        "(s.name like :text or s.stylistName like :text ) " +
                        "and s.status=true and s.deleted=false", Services.class)
                .setParameter("text", text.toUpperCase() + "%")
                .getResultList();
    }

    @Transactional
    public List<Services> findByServiceName(String serviceName) throws Exception {
        return entityManager
                .createQuery("select s from servicesEntity s where s.name like :name",Services.class)
                .setParameter("name",serviceName)
                .getResultList();

    }

    @Transactional
    public Services findByName(String name) throws Exception {
        List<Services> servicesList =
                entityManager
                        .createQuery("select s from servicesEntity s where s.name =:name and s.deleted=false", Services.class)
                        .setParameter("name", name.toUpperCase())
                        .getResultList();
        if (!servicesList.isEmpty()) {
            return servicesList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Services> findAvailableServices() throws Exception {
        return entityManager
                .createQuery("select s from servicesEntity s where s.available=true and s.status=true and s.deleted=false ", Services.class)
                .getResultList();
    }

    @Transactional
    public List<Services> findAvailableServicesByType(String servicesType) throws Exception {

        ServicesType enumServicesType = ServicesType.valueOf(servicesType.toUpperCase());

        return entityManager
                .createQuery("select s from servicesEntity s where s.available=true and s.status=true and s.servicesType=:servicesType and s.deleted=false ", Services.class)
                .setParameter("servicesType", enumServicesType)
                .getResultList();
    }

    @Transactional
    public List<Services> findBySalonId(Long salonId) throws Exception {
        return entityManager
                .createQuery("SELECT s.servicesList FROM salonEntity s WHERE s.id = :salonId ", Services.class)
                .setParameter("salonId", salonId)
                .getResultList();
    }
    @Transactional
    public List<Timing> findByServicesIdAndDateAndSalonId(Long servicesId, LocalDate date, Long salonId) throws Exception {
        return entityManager
                .createQuery("select t from timingEntity t where t.startTime between :startTime and :endTime and t.services.id =:servicesId and t.salon.id =:salonId and t.deleted = false ", Timing.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("servicesId", servicesId)
                .setParameter("salonId", salonId)
                .getResultList();
    }
}
