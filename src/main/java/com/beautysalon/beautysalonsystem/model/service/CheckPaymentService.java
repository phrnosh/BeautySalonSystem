package com.beautysalon.beautysalonsystem.model.service;


import com.beautysalon.beautysalonsystem.model.entity.CheckPayment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class CheckPaymentService implements Serializable  {
    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public CheckPayment save(CheckPayment checkPayment) throws Exception {
        entityManager.persist(checkPayment);
        return checkPayment;
    }

    @Transactional
    public CheckPayment edit(CheckPayment checkPayment) throws Exception {
        CheckPayment foundCheckPayment = entityManager.find(CheckPayment.class, checkPayment.getId());
        if (foundCheckPayment != null) {
            entityManager.merge(checkPayment);
        }
        return checkPayment;
    }

    @Transactional
    public CheckPayment remove(Long id) throws Exception {
        CheckPayment checkPayment = entityManager.find(CheckPayment.class, id);
        if (checkPayment != null) {
            checkPayment.setDeleted(true);
            entityManager.merge(checkPayment);
        }
        return checkPayment;
    }

    @Transactional
    public List<CheckPayment> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from checkPayment oo where oo.deleted=false", CheckPayment.class)
                .getResultList();
    }

    @Transactional
    public CheckPayment findById(Long id) throws Exception {
        return entityManager.find(CheckPayment.class, id);
    }

    @Transactional
    public List<CheckPayment> FindByCheckNumber(long checkNumber) throws Exception {
        return entityManager
                .createQuery("select oo from checkPayment oo where oo.checkNumber=:checkNumber", CheckPayment.class)
                .setParameter("checkNumber", checkNumber)
                .getResultList();
    }
    @Transactional
    public List<CheckPayment> FindByDateTime(LocalDateTime dateTime) throws Exception {
        return entityManager
                .createQuery("select oo from checkPayment oo where oo.paymentDateTime=:paymentDateTime", CheckPayment.class)
                .setParameter("paymentDateTime", dateTime)
                .getResultList();
    }
}
