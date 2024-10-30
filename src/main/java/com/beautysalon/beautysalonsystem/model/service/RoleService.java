package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class RoleService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Role save(Role role) throws Exception {
        entityManager.persist(role);
        return role;
    }

    @Transactional
    public Role edit(Role role) throws Exception {
        role = entityManager.find(Role.class, role.getRole());
        if (role != null) {
            entityManager.merge(role);
            return role;
        }
        throw new Exception();
    }

    @Transactional
    public Role remove(Long id) throws Exception {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            role.setDeleted(true);
            entityManager.merge(role);
        }
        return role;
    }

    @Transactional
    public List<Role> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from roleEntity oo where oo.deleted=false", Role.class)
                .getResultList();
    }

    @Transactional
    public Role findById(Long id) throws Exception {
        return entityManager.find(Role.class, id);
    }

    @Transactional
    public Role FindByRole(String name) throws Exception {
        List<Role> roleList =
                entityManager
                        .createQuery("select r from roleEntity r where r.role =:name and r.deleted=false ", Role.class)
                        .setParameter("name", name)
                        .getResultList();
        if (!roleList.isEmpty()) {
            return roleList.get(0);
        } else {
            return null;
        }
    }
}
