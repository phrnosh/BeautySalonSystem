package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BookingService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Booking save(Booking booking) throws Exception {
        entityManager.persist(booking);
        return booking;
    }

    @Transactional
    public Booking edit(Booking booking) throws Exception {
        Booking foundBooking = entityManager.find(Booking.class, booking.getId());
        if (foundBooking != null) {
            entityManager.merge(booking);
        }
        return booking;
    }

    @Transactional
    public Booking remove(Long id) throws Exception {
        Booking booking = entityManager.find(Booking.class,id );
        if (booking != null) {
            booking.setDeleted(true);
            entityManager.merge(booking);
        }
        return booking;
    }

    @Transactional
    public List<Booking> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from bookingEntity oo where oo.deleted=false", Booking.class)
                .getResultList();
    }

    @Transactional
    public Booking findById(Long id) throws Exception {
        return entityManager.find(Booking.class, id);
    }


    @Transactional
    public List<Booking> findByCustomerNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select b from bookingEntity b where b.customer.name like :name and b.customer.family like :family and b.deleted=false ", Booking.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public List<Booking> findByCustomerPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select b from bookingEntity b where b.customer.phoneNumber =:phoneNumber and b.deleted=false order by b.issueTime desc ", Booking.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    @Transactional
    public List<Long> findReservedTimingByServicesId(Long timingId) throws Exception {
        return entityManager
                .createQuery("select b.timing from bookingEntity b where b.timing.id =:timingId and b.reserved=true and b.deleted=false ", Long.class)
                .setParameter("timingId", timingId)
                .getResultList();
    }

    @Transactional
    public List<Long> findSoldTimingByServicesId(Long timingId) throws Exception {
        return entityManager
                .createQuery("select b.timing from bookingEntity b where b.timing.id =:timingId and b.reserved=false and b.deleted=false ", Long.class)
                .setParameter("timingId", timingId)
                .getResultList();
    }

    @Transactional
    public List<Booking> findFailedReserved() throws Exception {
        return entityManager
                .createQuery("select b from bookingEntity b where b.reserved=true and b.issueTime <: allowedTime and b.deleted=false ", Booking.class)
                .setParameter("allowedTime", LocalDateTime.now().minusMinutes(15))
                .getResultList();
    }

    @Transactional
    public List<Booking> findByTimingId(Long timingId) throws Exception {
        return entityManager
                .createQuery("select b from bookingEntity b where b.timing.id=:timingId and b.deleted=false order by b.issueTime desc ", Booking.class)
                .setParameter("timingId", timingId)
                .getResultList();
    }

//    @Transactional
//    public List<Booking> findByRangeDate(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
//        return entityManager
//                .createQuery("select b from bookingEntity b where b.localDateTime between :startTime and :endTime", Booking.class)
//                .setParameter("startTime", startDate)
//                .setParameter("endTime", endDate)
//                .getResultList();
//    }

//    @Transactional
//    public List<Booking> findByDate(LocalDateTime localDateTime) throws Exception {
//        return entityManager
//                .createQuery("select b from bookingEntity b where b.localDateTime =:localDateTime", Booking.class)
//                .setParameter("localDateTime", localDateTime )
//                .getResultList();
//    }

    @Transactional
    public Booking findByCustomer(String customerId) throws Exception {
        return entityManager
                .createQuery("select b from bookingEntity b where b.customer.id =: customer", Booking.class)
                .setParameter("customer", customerId)
                .getSingleResult();
    }

//    @Transactional
//    public List<Booking> findByServiceName(String servicesList) throws Exception {
//        return entityManager
//                .createQuery("select b from bookingEntity b where b.servicesList =: servicesList", Booking.class)
//                .setParameter("servicesList", servicesList)
//                .getResultList();
//    }
//    @Transactional
//    public List<Booking> findByCancelBooked(String servicesList) throws Exception {
//        return entityManager
//                .createQuery("select b from bookingEntity b where b.servicesList =: servicesList", Booking.class)
//                .setParameter("servicesList", servicesList)
//                .getResultList();
//    }

    @Transactional
    public List<Booking> findBySalonId(Long salonId) throws Exception {
        return entityManager
                .createQuery("select b from bookingEntity b where b.timing.salon.id =: salonId", Booking.class)
                .setParameter("salonId", salonId)
                .getResultList();
    }

}
