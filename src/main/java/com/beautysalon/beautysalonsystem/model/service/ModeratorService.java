package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Moderator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ModeratorService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Moderator save(Moderator moderator) throws Exception {
        entityManager.persist(moderator);
        return moderator;
    }

    @Transactional
    public Moderator edit(Moderator moderator) throws Exception {
        Moderator foundModerator = entityManager.find(Moderator.class, moderator.getId());
        if (foundModerator != null) {
            entityManager.merge(moderator);
            return moderator;
        }
        throw new Exception();
    }

    @Transactional
    public Moderator remove(Long id) throws Exception {
        Moderator moderator = entityManager.find(Moderator.class, id);
        if (moderator != null) {
            moderator.setDeleted(true);
            entityManager.merge(moderator);
            return moderator;
        }
        throw new Exception();
    }

    @Transactional
    public List<Moderator> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from moderatorEntity oo where oo.deleted=false order by id", Moderator.class)
                .getResultList();
    }

    @Transactional
    public Moderator findById(Long id) throws Exception {
        return entityManager.find(Moderator.class, id);
    }

    @Transactional
    public List<Moderator> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select m from moderatorEntity m where m.name like :name and m.family like :family and m.deleted=false ", Moderator.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public Moderator findByPhoneNumber(String phoneNumber) throws Exception {
        List<Moderator> moderatorList =
                entityManager
                        .createQuery("select m from moderatorEntity m where m.phoneNumber =:phoneNumber and m.deleted=false ", Moderator.class)
                        .setParameter("phoneNumber", phoneNumber)
                        .getResultList();
        if (!moderatorList.isEmpty()){
            return moderatorList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Moderator findByUsername(String username) {
        List<Moderator> moderatorList =
                entityManager
                        .createQuery("select m from moderatorEntity m where m.user.username =:username and m.deleted=false ", Moderator.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!moderatorList.isEmpty()){
            return moderatorList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Moderator findByNationalCode(String nationalCode) throws Exception {
        List<Moderator> moderatorList =
                entityManager
                        .createQuery("select m from moderatorEntity m where m.nationalCode =:nationalCode and m.deleted=false ", Moderator.class)
                        .setParameter("nationalCode", nationalCode)
                        .getResultList();
        if (!moderatorList.isEmpty()){
            return moderatorList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Moderator> findByFamily(String family) throws Exception {
        return entityManager
                .createQuery("select m from moderatorEntity m where m.family like :family and m.deleted=false ", Moderator.class)
                .setParameter("family", family.toUpperCase() + "%")
                .getResultList();
    }
}