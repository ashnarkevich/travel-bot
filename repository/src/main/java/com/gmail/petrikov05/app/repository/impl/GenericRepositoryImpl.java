package com.gmail.petrikov05.app.repository.impl;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gmail.petrikov05.app.repository.GenericRepository;
import com.gmail.petrikov05.app.repository.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    private final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[1];
    }

    @Override
    public void add(T t) throws RepositoryException {
        try {
            entityManager.persist(t);
        } catch (Exception e) {
            logger.info("exception add new entity: {}, {}",
                    entityClass.getSimpleName(), e.getStackTrace());
            throw new RepositoryException();
        }
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    public void update(T t) {
        entityManager.merge(t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        String queryString = "FROM " + entityClass.getSimpleName() + " c";
        Query query = entityManager.createQuery(queryString);
        return (List<T>) query.getResultList();
    }

}
