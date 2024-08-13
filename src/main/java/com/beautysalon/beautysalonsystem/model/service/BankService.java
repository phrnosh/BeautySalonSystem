package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Bank;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@RequestScoped
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
        return entityManager
                .createQuery("select oo from bankEntity oo where oo.deleted=false", Bank.class)
                .getResultList();
    }

    @Transactional
    public Bank findById(Long id) throws Exception {
        return entityManager.find(Bank.class, id);
    }

    @Transactional
    public Bank findByName(String name) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select be from bankEntity be where be.name=:name and be.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }

    @Transactional
    public Bank findAccountNumber(String accountNumber) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select be from bankEntity be where be.accountNumber=:accountNumber and be.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }

    @Transactional
    public Bank findByBranchName(String branchName) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select be from bankEntity be where be.branchName=:branchName and be.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }

    @Transactional
    public Bank findByBranchCode(Long branchCode) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select be from bankEntity be where be.branchCode=:branchCode and be.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }
}