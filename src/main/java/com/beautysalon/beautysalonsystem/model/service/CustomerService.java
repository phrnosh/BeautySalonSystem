package com.beautysalon.beautysalonsystem.model.service;


import com.beautysalon.beautysalonsystem.model.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class CustomerService implements Serializable {
    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Customer save(Customer customer) throws Exception {
        entityManager.persist(customer);
        return customer;
    }

    @Transactional
    public Customer edit(Customer customer) throws Exception {
        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());
        if (foundCustomer != null) {
            entityManager.merge(customer);
        }
        return customer;
    }

    @Transactional
    public Customer remove(Long id) throws Exception {
        Customer customer = entityManager.find(Customer.class,id );
        if (customer != null) {
            customer.setDeleted(true);
            entityManager.merge(customer);
        }
        return customer;
    }

    @Transactional
    public List<Customer> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from customerEntity oo where oo.deleted=false", Customer.class)
                .getResultList();
    }

    @Transactional
    public Customer findById(Long id) throws Exception {
        return entityManager.find(Customer.class, id);
    }

    @Transactional
    public Customer findByUsername(String username) throws Exception {
        List<Customer> customerList =
                entityManager
                        .createQuery("select c from customerEntity c where c.user.username =:username and c.deleted=false ", Customer.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!customerList.isEmpty()) {
            return customerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Customer> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.name like :name and c.family like :family", Customer.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public List<Customer> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.user.username=:username and c.user.password=:password", Customer.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    @Transactional
    public List<Customer> findByEmail(String email) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.email like :email", Customer.class)
                .setParameter("email", email )
                .getResultList();
    }

    @Transactional
    public Customer findByPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.phoneNumber =:phoneNumber", Customer.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }

    @Transactional
    public List<Customer> findByFamily(String family) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.family like :family and c.deleted=false ", Customer.class)
                .setParameter("family", family.toUpperCase() + "%")
                .getResultList();
    }
}