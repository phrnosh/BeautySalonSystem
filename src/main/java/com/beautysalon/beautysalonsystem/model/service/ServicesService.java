package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Services;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
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
                .createQuery("select n from servicesEntity n where n .deleted=false", Services.class)
                .getResultList();
    }

    @Transactional
    public Services findById(Long id) throws Exception {
        return entityManager.find(Services.class,id);
    }

    @Transactional
    public List<Services> findByStylist(String name, String family) throws Exception {
        return entityManager
                .createQuery("select m from servicesEntity m where m.stylist.name like :name and m.stylist.family like :family", Services.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }


    @Transactional
    public List<Services> findByServiceName(String serviceName) throws Exception {
        return entityManager
                .createQuery("select m from servicesEntity m where m.name like :name",Services.class)
                .setParameter("name",serviceName)
                .getResultList();

    }
}