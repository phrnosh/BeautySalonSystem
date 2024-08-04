package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Role;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Singleton
public class RoleService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    public Role save(Role role) throws Exception {
        entityManager.persist(role);
        return role;
    }

    public Role edit(Role role) throws Exception {
        return role;
    }

    public Role remove(Long id) throws Exception {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            role.setDeleted(true);
            entityManager.merge(role);
        }
        return role;
    }

    public List<Role> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from roleEntity oo where oo.deleted=false", Role.class)
                .getResultList();
    }

    public Role findById(Long id) throws Exception {
        return entityManager.find(Role.class, id);
    }

    public List<Role> FindByRole(String role) throws Exception {
        return entityManager
                .createQuery("select r from roleEntity r where r.role = :role", Role.class)
                .setParameter("role", role)
                .getResultList();
    }
}
