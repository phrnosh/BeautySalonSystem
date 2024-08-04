package com.beautysalon.beautysalonsystem.model.service;


import com.beautysalon.beautysalonsystem.model.entity.Customer;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Singleton
public class CustomerService implements Serializable {
    @PersistenceContext(unitName = "beautySalon")
    private EntityManager entityManager;


    public Customer save(Customer customer) throws Exception {
        entityManager.persist(customer);
        return customer;
    }

    public Customer edit(Customer customer) throws Exception {
        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());
        if (foundCustomer != null) {
            entityManager.merge(customer);
        }
        return customer;
    }

    public Customer remove(Long id) throws Exception {
        Customer customer = entityManager.find(Customer.class,id );
        if (customer != null) {
            customer.setDeleted(true);
            entityManager.merge(customer);
        }
        return customer;
    }

    public List<Customer> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from customerEntity oo where oo.deleted=false", Customer.class)
                .getResultList();
    }
    public Customer findById(Long id) throws Exception {
        return entityManager.find(Customer.class, id);
    }

    public List<Customer> findByUsername(String username) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.user.username=:username", Customer.class)
                .setParameter("username", username )
                .getResultList();
    }

    public List<Customer> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.name like :name and c.family like :family", Customer.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    public List<Customer> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.user.username=:username and c.user.password=:password", Customer.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }

    public List<Customer> findByEmail(String email) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.email like :email", Customer.class)
                .setParameter("email", email )
                .getResultList();
    }

    public Customer findByPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.phoneNumber =:phoneNumber", Customer.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }
    public Customer findByNationalCode(String nationalCode) throws Exception {
        return entityManager
                .createQuery("select c from customerEntity c where c.nationalCode=:nationalCode", Customer.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
    }
}