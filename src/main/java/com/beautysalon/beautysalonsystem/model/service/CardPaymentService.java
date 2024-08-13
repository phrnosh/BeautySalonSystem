package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.CardPayment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class CardPaymentService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;


    @Transactional
    public CardPayment save(CardPayment cardPayment) throws Exception {
        entityManager.persist(cardPayment);
        return cardPayment;
    }
    @Transactional
    public CardPayment edit(CardPayment cardPayment) throws Exception {
        CardPayment foundCardPayment = entityManager.find(CardPayment.class, cardPayment.getId());
        if (foundCardPayment != null) {
            entityManager.merge(cardPayment);
        }
        return cardPayment;
    }

    @Transactional
    public CardPayment remove(Long id) throws Exception {
        CardPayment cardPayment = entityManager.find(CardPayment.class, id);
        if (cardPayment != null) {
            cardPayment.setDeleted(true);
            entityManager.merge(cardPayment);
        }
        return cardPayment;
    }

    @Transactional
    public List<CardPayment> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from cardPayment oo where oo.deleted=false", CardPayment.class)
                .getResultList();
    }

    @Transactional
    public CardPayment findById(Long id) throws Exception {
        return entityManager.find(CardPayment.class, id);
    }

    @Transactional
    public List<CardPayment> FindByCardNumber(long cardNumber) throws Exception {
        return entityManager
                .createQuery("select oo from cardPayment oo where oo.cardNumber=:cardNumber", CardPayment.class)
                .setParameter("cardNumber", cardNumber)
                .getResultList();
    }

    @Transactional
    public List<CardPayment> FindByDateTime(LocalDateTime dateTime) throws Exception {
        return entityManager
                .createQuery("select oo from cardPayment oo where oo.paymentDateTime=:paymentDateTime", CardPayment.class)
                .setParameter("paymentDateTime", dateTime)
                .getResultList();
    }
}
