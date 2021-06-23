package com.gmail.petrikov05.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.gmail.petrikov05.app.repository.CityRepository;
import com.gmail.petrikov05.app.repository.exception.RepositoryException;
import com.gmail.petrikov05.app.repository.model.City;
import com.gmail.petrikov05.app.service.CityService;
import com.gmail.petrikov05.app.service.exception.ExistEntityException;
import com.gmail.petrikov05.app.service.exception.ServiceException;
import com.gmail.petrikov05.app.service.model.AddCityDTO;
import com.gmail.petrikov05.app.service.model.CityDTO;
import com.gmail.petrikov05.app.service.model.UpdateCityDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {this.cityRepository = cityRepository;}

    @Override
    public CityDTO add(AddCityDTO addCityDTO) throws ServiceException, ExistEntityException {
        boolean isExistCity = checkExistCity(addCityDTO.getName());
        if (isExistCity) {
            throw new ExistEntityException();
        }
        try {
            City city = convertAddDTOToObject(addCityDTO);
            cityRepository.add(city);
            return convertObjectToDTO(city);
        } catch (RepositoryException e) {
            logger.info("exception in city repository add method");
            throw new ServiceException();
        }
    }

    @Override
    public CityDTO deleteById(UUID id) throws ExistEntityException {
        Optional<City> optionalCity = cityRepository.getById(id);
        City city = optionalCity.orElseThrow(ExistEntityException::new);
        cityRepository.delete(city);
        return convertObjectToDTO(city);
    }

    @Override
    public CityDTO updateById(UUID id, UpdateCityDTO updateCityDTO) throws ExistEntityException {
        Optional<City> optionalCity = cityRepository.getById(id);
        City city = optionalCity.orElseThrow(ExistEntityException::new);
        getUpdatedCity(city, updateCityDTO);
        cityRepository.update(city);
        return convertObjectToDTO(city);
    }

    @Override
    public CityDTO findByName(String name) throws ExistEntityException {
        Optional<City> optionalCity = cityRepository.getByName(name);
        City city = optionalCity.orElseThrow(ExistEntityException::new);
        return convertObjectToDTO(city);
    }

    @Override
    public List<CityDTO> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(this::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    private void getUpdatedCity(City city, UpdateCityDTO updateCity) {
        if (updateCity.getName() != null && !updateCity.getName().equals(city.getName())) {
            city.setName(updateCity.getName());
        }
        if (updateCity.getInfo() != null && !updateCity.getInfo().equals(city.getInfo())) {
            city.setInfo(updateCity.getInfo());
        }
    }

    private boolean checkExistCity(String name) {
        Optional<City> optionalCity = cityRepository.getByName(name);
        return optionalCity.isPresent();
    }

    private CityDTO convertObjectToDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setInfo(city.getInfo());
        return cityDTO;
    }

    private City convertAddDTOToObject(AddCityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        city.setInfo(cityDTO.getInfo());
        return city;
    }

}
