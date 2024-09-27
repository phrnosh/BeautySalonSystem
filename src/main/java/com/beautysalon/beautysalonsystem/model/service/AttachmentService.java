package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Attachment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;

@ApplicationScoped
public class AttachmentService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;


    @Transactional
    public Attachment remove(Long id) throws Exception {
        Attachment attachment = entityManager.find(Attachment.class, id);
        if (attachment != null) {
            entityManager.remove(attachment);
            entityManager.flush();
            return attachment;
        }
        throw new Exception();
    }


}