package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Bank;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class BankService implements Serializable {
    @PersistenceContext(unitName = "beautysalon")
    private EntityManager entityManager;

    @Transactional
    public Bank save(Bank bank) throws Exception {
        entityManager.persist(bank);
        return bank;
    }

    @Transactional
    public Bank edit(Bank bank) throws Exception {
        Bank foundBank = entityManager.find(Bank.class, bank.getId());
        if (foundBank != null) {
            entityManager.merge(bank);
            return bank;
        }
        throw new Exception();
    }

    @Transactional
    public Bank remove(Long id) throws Exception {
        Bank bank = entityManager.find(Bank.class, id);
        if (bank != null) {
            bank.setDeleted(true);
            entityManager.merge(bank);
            return bank;
        }
        throw new Exception();
    }

    @Transactional
    public List<Bank> findAll() throws Exception {
        return entityManager.createQuery("select b from bankEntity b where b.deleted=false order by id", Bank.class)
                .getResultList();
    }

    @Transactional
    public Bank findById(Long id) throws Exception {
        return entityManager.find(Bank.class, id);
    }

    @Transactional
    public Bank findByName(String name) throws Exception {
        List<Bank> bankList = entityManager
                .createQuery("select b from bankEntity b where b.name=:name and b.deleted=false", Bank.class)
                .setParameter("name", name)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Bank findByAccountNumber(String accountNumber) throws Exception {
        List<Bank> bankList = entityManager
                .createQuery("select b from bankEntity b where b.accountNumber=:accountNumber and b.deleted=false", Bank.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Bank findByBranchName(String branchName) throws Exception {
        List<Bank> bankList = entityManager
                .createQuery("select b from bankEntity b where b.branchName=:branchName and b.deleted=false", Bank.class)
                .setParameter("branchName", branchName)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        } else {
            return null;
        }

    }

    @Transactional
    public Bank findByBranchCode(Long branchCode) throws Exception {
        List<Bank> bankList = entityManager
                .createQuery("select b from bankEntity b where b.branchCode=:branchCode and b.deleted=false", Bank.class)
                .setParameter("branchCode", branchCode)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Bank> findByStatus(boolean status) throws Exception {
        return entityManager
                .createQuery("select b from bankEntity b where b.status=:status and b.deleted=false", Bank.class)
                .setParameter("status", status)
                .getResultList();

    }

}