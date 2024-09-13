package com.store.onlinestore.model.repository;

import com.store.onlinestore.model.utils.JpaProvider;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

public class CrudRepository<T, I> implements AutoCloseable {
    private EntityManager entityManager;

    public T save(T t) {
        entityManager = JpaProvider.getJpa().getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(t);
        entityTransaction.commit();
        return t;
    }

    public T edit(T t) {
        entityManager = JpaProvider.getJpa().getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(t);
        entityTransaction.commit();
        return t;
    }

    public T remove(I id, Class<T> tClass) {
        entityManager = JpaProvider.getJpa().getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        T t = entityManager.find(tClass, id);
        entityManager.remove(t);
        entityTransaction.commit();
        return t;
    }

    public List<T> findAll(Class<T> tClass) {
        entityManager = JpaProvider.getJpa().getEntityManager();

        TypedQuery<T> query = entityManager.createQuery(String.format("select oo from %s oo", tClass.getAnnotation(Entity.class).name()), tClass);
        return query.getResultList();
    }

    public T findById(I id, Class<T> tClass) {
        entityManager = JpaProvider.getJpa().getEntityManager();
        return entityManager.find(tClass, id);
    }

    public List<T> executeQuery(String queryName,  Map<String, Object> paramsMap, Class<T> tClass) {
        entityManager = JpaProvider.getJpa().getEntityManager();
        TypedQuery<T> query = entityManager.createNamedQuery(queryName, tClass);
        for (String key : paramsMap.keySet()) {
            query.setParameter(key, paramsMap.get(key));
        }
        return query.getResultList();
    }


    @Override
    public void close() throws Exception {
        entityManager.close();
    }
}
