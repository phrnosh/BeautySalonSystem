package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Services;
import com.beautysalon.beautysalonsystem.model.entity.Timing;
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
public class TimingService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Timing save(Timing timing) throws Exception {
        entityManager.persist(timing);
        return timing;
    }

    @Transactional
    public Timing edit(Timing timing) throws Exception {
        Timing foundTiming = entityManager.find(Timing.class, timing.getId());
        if (foundTiming != null) {
            entityManager.merge(timing);
        }
        return timing;
    }

    @Transactional
    public Timing remove(Long id) throws Exception {
        Timing timing =entityManager.find(Timing.class, id);
        if(timing !=null){
            timing.setDeleted(true);
            entityManager.merge(timing);
        }
        return timing;
    }

    @Transactional
    public List<Timing> findAll() throws Exception {
        return entityManager
                .createQuery("select t from timingEntity t where t.deleted=false", Timing.class)
                .getResultList();
    }

    @Transactional
    public Timing findById(Long id) throws Exception {
        return entityManager.find(Timing.class,id);
    }

    @Transactional
    public List<Timing> findBySalonName(String name) throws Exception {
        return entityManager
                .createQuery("select t from timingEntity t where t.salon.name =:name and t.deleted=false", Timing.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Transactional
    public List<Timing> findByServicesId(Long servicesId) throws Exception {
        return entityManager
                .createQuery("select t from timingEntity t where t.startTime between :startTime and :endTime and t.services.id =:servicesId and t.deleted = false ", Timing.class)
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
                        .createQuery("select distinct t.startTime from timingEntity t where t.startTime between :startTime and :endTime and t.services.id =:servicesId and t.deleted = false ", LocalDateTime.class)
                        .setParameter("startTime", LocalDateTime.now())
                        .setParameter("endTime", LocalDateTime.now().plusDays(7))
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
    public List<Timing> findByServicesIdAndDate(Long servicesId, LocalDate date) throws Exception {
        return entityManager
                .createQuery("select t from timingEntity t where t.startTime between :startTime and :endTime and t.services.id =:servicesId and t.deleted = false ", Timing.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("servicesId", servicesId)
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

    @Transactional
    public List<Services> findActiveServices() throws Exception {
        return entityManager
                .createQuery("select distinct t.services from timingEntity t where t.startTime between :startTime and :endTime and  t.deleted = false ", Services.class)
                .setParameter("startTime", LocalDateTime.now())
                .setParameter("endTime", LocalDateTime.now().plusDays(20))
                .getResultList();
    }

    @Transactional
    public List<Salon> findDistinctSalonByServicesIdAndDate(Long servicesId, LocalDate date) throws Exception {
        return entityManager
                .createQuery("select distinct t.salon from timingEntity t where t.startTime between :startTime and :endTime and t.services.id = :servicesId and t.status = true and t.deleted = false and t.salon.status=true ", Salon.class)
                .setParameter("startTime", date.atTime(1, 0, 0))
                .setParameter("endTime", date.atTime(23, 59, 59))
                .setParameter("servicesId", servicesId)
                .getResultList();
    }

    @Transactional
    public List<Timing> findTimingByServicesIdAndTime(Long servicesId, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        return entityManager
                .createQuery("select t from timingEntity t where t.deleted=false and t.services.id =: servicesId and (" +
                        "(t.startTime between :startTime and :endTime)" +
                        " or (t.endTime between :startTime and :endTime)" +
                        " or (:startTime between t.startTime and t.endTime)" +
                        ")", Timing.class)
                .setParameter("servicesId", servicesId)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .getResultList();
    }
}
