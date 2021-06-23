package com.gmail.petrikov05.app.service;

import java.util.List;
import java.util.UUID;

import com.gmail.petrikov05.app.service.exception.ExistEntityException;
import com.gmail.petrikov05.app.service.exception.ServiceException;
import com.gmail.petrikov05.app.service.model.AddCityDTO;
import com.gmail.petrikov05.app.service.model.CityDTO;
import com.gmail.petrikov05.app.service.model.UpdateCityDTO;

public interface CityService {

    CityDTO add(AddCityDTO cityDTO) throws ServiceException, ExistEntityException;

    CityDTO deleteById(UUID id) throws ExistEntityException;

    CityDTO updateById(UUID id, UpdateCityDTO updateCityDTO) throws ExistEntityException;

    CityDTO findByName(String name) throws ExistEntityException;

    List<CityDTO> getAllCities();

}
