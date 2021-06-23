package com.gmail.petrikov05.app.repository.impl;

import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.gmail.petrikov05.app.repository.CityRepository;
import com.gmail.petrikov05.app.repository.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepositoryImpl extends GenericRepositoryImpl<UUID, City> implements CityRepository {

    private final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Optional<City> getByName(String name) {
        String queryString = "FROM " + entityClass.getSimpleName() + " AS c WHERE c.name = :name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);
        try {
            return Optional.of((City) query.getSingleResult());
        } catch (NoResultException e) {
            logger.info("The city with the same name ({}) not found.", name);
            return Optional.empty();
        }
    }

    @Override
    public Optional<City> getById(UUID id) {
        String queryStr = "FROM " + entityClass.getSimpleName() + " AS c WHERE c.id=:id";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("id", id);
        try {
            return Optional.of((City) query.getSingleResult());
        } catch (NoResultException e) {
            logger.info("The city with the same id ({}) not found.", id);
            return Optional.empty();
        }
    }

}
