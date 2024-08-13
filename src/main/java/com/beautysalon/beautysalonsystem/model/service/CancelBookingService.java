package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Booking;
import com.beautysalon.beautysalonsystem.model.entity.CancelBooking;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
public class CancelBookingService implements Serializable {
    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public CancelBooking remove(Long id) throws Exception {
        CancelBooking cancelBooking = entityManager.find(CancelBooking.class,id );
        if (cancelBooking != null) {
            cancelBooking.setDeleted(true);
            entityManager.merge(cancelBooking);
        }
        return cancelBooking;
    }
}
