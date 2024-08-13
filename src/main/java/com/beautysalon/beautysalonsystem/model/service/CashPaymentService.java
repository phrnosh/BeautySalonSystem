package com.beautysalon.beautysalonsystem.model.service;



import com.beautysalon.beautysalonsystem.model.entity.CashPayment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class CashPaymentService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public CashPayment save(CashPayment cashPayment) throws Exception {
        entityManager.persist(cashPayment);
        return cashPayment;
    }

    @Transactional
    public CashPayment edit(CashPayment cashPayment) throws Exception {
        CashPayment foundCashPayment = entityManager.find(CashPayment.class, cashPayment.getId());
        if (foundCashPayment != null) {
            entityManager.merge(cashPayment);
        }
        return cashPayment;
    }

    @Transactional
    public CashPayment remove(Long id) throws Exception {
        CashPayment cashPayment = entityManager.find(CashPayment.class, id);
        if (cashPayment != null) {
            cashPayment.setDeleted(true);
            entityManager.merge(cashPayment);
        }
        return cashPayment;
    }

    @Transactional
    public List<CashPayment> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from cashPayment oo where oo.deleted=false", CashPayment.class)
                .getResultList();
    }

    @Transactional
    public CashPayment findById(Long id) throws Exception {
        return entityManager.find(CashPayment.class, id);
    }

    @Transactional
    public List<CashPayment> FindByDateTime(LocalDateTime dateTime) throws Exception {
            return entityManager
                    .createQuery("select oo from cashPayment oo where oo.paymentDateTime=:paymentDateTime", CashPayment.class)
                    .setParameter("paymentDateTime", dateTime)
                    .getResultList();
        }
    }
