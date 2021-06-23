package com.gmail.petrikov05.app.repository;

import java.util.Optional;
import java.util.UUID;

import com.gmail.petrikov05.app.repository.model.City;

public interface CityRepository extends GenericRepository<UUID, City> {

    Optional<City> getByName(String name);

    Optional<City> getById(UUID id);

}
