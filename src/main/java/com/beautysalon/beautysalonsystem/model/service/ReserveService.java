package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.Reserve;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReserveService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Reserve save(Reserve reserve) throws Exception {
        entityManager.persist(reserve);
        return reserve;
    }

    @Transactional
    public Reserve edit(Reserve reserve) throws Exception {
        Reserve foundReserve = entityManager.find(Reserve.class, reserve.getId());
        if (foundReserve != null) {
            entityManager.merge(reserve);
        }
        return reserve;
    }

    @Transactional
    public Reserve remove(Long id) throws Exception {
        Reserve reserve=entityManager.find(Reserve.class, id);
        if(reserve!=null){
            reserve.setDeleted(true);
            entityManager.merge(reserve);
        }
        return reserve;
    }

    @Transactional
    public List<Reserve> findAll() throws Exception {
        return entityManager
                .createQuery("select r from reserveEntity r where r.deleted=false", Reserve.class)
                .getResultList();
    }

    @Transactional
    public Reserve findById(Long id) throws Exception {
        return entityManager.find(Reserve.class,id);
    }

    @Transactional
    public List<Reserve> findBySalonName(String name) throws Exception {
        return entityManager
                .createQuery("select r from reserveEntity r where r.salon.name =:name and r.deleted=false", Reserve.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Transactional
    public List<Reserve> findByServicesId(Long servicesId) throws Exception {
        return entityManager
                .createQuery("select r from reserveEntity r where r.startTime between :startTime and :endTime and r.services.id =:servicesId and r.deleted = false ", Reserve.class)
                .setParameter("startTime", LocalDateTime.now())
                .setParameter("endTime", LocalDateTime.now())
                .setParameter("servicesId", servicesId)
                .getResultList();
    }

    @Transactional
    public List<LocalDate> findDistinctDatesByServicesId(Long servicesId) throws Exception {
        List<LocalDate> dates = new ArrayList<>();
        List<LocalDateTime> dateTimeList =
                entityManager
                        .createQuery("select distinct r.startTime from reserveEntity r where r.startTime between :startTime and :endTime and r.services.id =:servicesId and r.deleted = false ", LocalDateTime.class)
                        .setParameter("startTime", LocalDateTime.now())
                        .setParameter("endTime", LocalDateTime.now().plusDays(5))
                        .setParameter("servicesId", servicesId)
                        .getResultList();
        for (LocalDateTime localDateTime : dateTimeList) {
            LocalDate date = localDateTime.toLocalDate();
            if (!dates.contains(date)) {
                dates.add(date);
            }
        }
        return dates;
    }

    @Transactional
    public List<Reserve> findByServicesIdAndDate(Long servicesId, LocalDate date) throws Exception {
        return entityManager
                .createQuery("select r from reserveEntity r where r.startTime between :startTime and :endTime and r.services.id =:servicesId and r.deleted = false ", Reserve.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("servicesId", servicesId)
                .getResultList();
    }

    @Transactional
    public List<Reserve> findByServicesIdAndDateAndSalonId(Long servicesId, LocalDate date, Long salonId) throws Exception {
        return entityManager
                .createQuery("select r from reserveEntity r where r.startTime between :startTime and :endTime and r.services.id =:servicesId and r.salon.id =:salonId and r.deleted = false ", Reserve.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("servicesId", servicesId)
                .setParameter("salonId", salonId)
                .getResultList();
    }

}
