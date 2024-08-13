package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.ShowBookingTime;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ShowBookingTimeService implements Serializable {
    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public ShowBookingTime save(ShowBookingTime showBookingTime) throws Exception {
        entityManager.persist(showBookingTime);
        return showBookingTime;
    }

    @Transactional
    public ShowBookingTime edit(ShowBookingTime showBookingTime) throws Exception {
        ShowBookingTime foundShowBookingTime = entityManager.find(ShowBookingTime.class, showBookingTime.getId());
        if (foundShowBookingTime != null) {
            entityManager.merge(showBookingTime);
        }
        return showBookingTime;
    }

    @Transactional
    public ShowBookingTime remove(Long id) throws Exception {
        ShowBookingTime showBookingTime = entityManager.find(ShowBookingTime.class,id );
        if (showBookingTime != null) {
            showBookingTime.setDeleted(true);
            entityManager.merge(showBookingTime);
        }
        return showBookingTime;
    }

    @Transactional
    public List<ShowBookingTime> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from showBookingTimeEntity oo where oo.deleted=false", ShowBookingTime.class)
                .getResultList();
    }

    @Transactional
    public ShowBookingTime findById(Long id) throws Exception {
        return entityManager.find(ShowBookingTime.class, id);
    }

    @Transactional
    public List<ShowBookingTime> findByRangeDate(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        return entityManager
                .createQuery("select sh from showBookingTimeEntity sh where sh.reservationDate between :startTime and :endTime", ShowBookingTime.class)
                .setParameter("startTime", startDate)
                .setParameter("endTime", endDate)
                .getResultList();
    }

    @Transactional
    public List<ShowBookingTime> findByDate(LocalDateTime reservationDate) throws Exception {
        return entityManager
                .createQuery("select sh from showBookingTimeEntity sh where sh.reservationDate =:reservationDate", ShowBookingTime.class)
                .setParameter("reservationDate", reservationDate )
                .getResultList();
    }

    @Transactional
    public List<ShowBookingTime> findByStylistName(String stylist) throws Exception {
        return entityManager
                .createQuery("select sh from showBookingTimeEntity sh where sh.stylist =: stylist", ShowBookingTime.class)
                .setParameter("stylist", stylist)
                .getResultList();
    }

    @Transactional
    public List<ShowBookingTime> findByServiceName(String servicesList) throws Exception {
        return entityManager
                .createQuery("select sh from showBookingTimeEntity sh where sh.services =: servicesList", ShowBookingTime.class)
                .setParameter("servicesList", servicesList)
                .getResultList();
    }
}
