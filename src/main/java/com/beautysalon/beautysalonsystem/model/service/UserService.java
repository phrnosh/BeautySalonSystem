package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UserService implements Serializable {

    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public User save(User user) throws Exception {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User edit(User user) throws Exception {
        User foundAdmin = entityManager.find(User.class, user.getUsername());
        if (foundAdmin != null) {
            entityManager.merge(user);
        }
        return user;
    }

    @Transactional
    public User remove(Long id) throws Exception {
        User user = entityManager.find(User.class,id );
        if (user != null) {
            user.setDeleted(true);
            entityManager.merge(user);
        }
        return user;
    }

    @Transactional
    public List<User> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from userEntity oo where oo.deleted=false", User.class)
                .getResultList();
    }

    @Transactional
    public User findById(Long id) throws Exception {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public User findByUsername(String username) throws Exception {
        List<User> userList =
                entityManager
                        .createQuery("select u from userEntity u where u.username=:username", User.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<User> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select u from userEntity u where u.username=:username and u.password=:password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional
    public List<User> findByRole(String role) throws Exception {
        return entityManager
                .createQuery("select u from userEntity u where u.role.role=:role", User.class)
                .setParameter("role", role)
                .getResultList();

    }
}