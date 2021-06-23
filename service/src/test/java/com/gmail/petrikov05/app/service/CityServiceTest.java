package com.gmail.petrikov05.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gmail.petrikov05.app.repository.CityRepository;
import com.gmail.petrikov05.app.repository.exception.RepositoryException;
import com.gmail.petrikov05.app.repository.model.City;
import com.gmail.petrikov05.app.service.exception.ExistEntityException;
import com.gmail.petrikov05.app.service.exception.ServiceException;
import com.gmail.petrikov05.app.service.impl.CityServiceImpl;
import com.gmail.petrikov05.app.service.model.AddCityDTO;
import com.gmail.petrikov05.app.service.model.CityDTO;
import com.gmail.petrikov05.app.service.model.UpdateCityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gmail.petrikov05.app.service.constant.TestConstant.VALID_CITY_ID;
import static com.gmail.petrikov05.app.service.constant.TestConstant.VALID_CITY_INFO;
import static com.gmail.petrikov05.app.service.constant.TestConstant.VALID_CITY_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class CityServiceTest {

    private CityService cityService;
    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    private void setUp() {
        this.cityService = new CityServiceImpl(cityRepository);
    }

    @Test
    public void whenAddCity_returnNotNull() throws ServiceException, ExistEntityException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        CityDTO actualResult = cityService.add(addCity);
        assertThat(actualResult).isNotNull();
    }

    @Test
    public void whenAddCity_returnCityDTO() throws ServiceException, ExistEntityException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        CityDTO actualResult = cityService.add(addCity);
        assertThat(actualResult).isInstanceOf(CityDTO.class);
    }

    @Test
    public void whenAddCity_callLogic() throws ServiceException, RepositoryException, ExistEntityException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        cityService.add(addCity);
        verify(cityRepository, times(1)).add(any(City.class));
    }

    @Test
    public void whenAddCity_returnCityWithValidName() throws ServiceException, ExistEntityException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        CityDTO actualResult = cityService.add(addCity);
        assertThat(actualResult.getName()).isEqualTo(VALID_CITY_NAME);
    }

    @Test
    public void whenAddCity_returnCityWithValidInfo() throws ServiceException, ExistEntityException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        CityDTO actualResult = cityService.add(addCity);
        assertThat(actualResult.getInfo()).isEqualTo(VALID_CITY_INFO);
    }

    @Test
    public void whenAddCity_returnServiceException() throws ServiceException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        doThrow(new RepositoryException()).when(cityRepository).add(any());
        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> cityService.add(addCity));
    }

    @Test
    public void whenAddAlreadyExistCity_callLogic() throws ServiceException, ExistEntityException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        cityService.add(addCity);
        verify(cityRepository, times(1)).getByName(VALID_CITY_NAME);
    }

    @Test
    public void whenAddAlreadyExistCity_returnExistEntityException() throws ServiceException, RepositoryException {
        AddCityDTO addCity = getValidAddCityDTO();
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getByName(anyString())).thenReturn(optionalReturnedCity);
        assertThatExceptionOfType(ExistEntityException.class)
                .isThrownBy(() -> cityService.add(addCity));
    }

    /* delete city by id */
    @Test
    public void whenDeleteCity_returnNotNull() throws ExistEntityException {
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getById(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.deleteById(VALID_CITY_ID);
        assertThat(actualResult).isNotNull();
    }

    @Test
    public void whenDeleteCity_returnCityDTO() throws ExistEntityException {
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getById(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.deleteById(VALID_CITY_ID);
        assertThat(actualResult).isInstanceOf(CityDTO.class);
    }

    @Test
    public void whenDeleteCity_callLogic() throws ExistEntityException {
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getById(any())).thenReturn(optionalReturnedCity);
        cityService.deleteById(VALID_CITY_ID);
        verify(cityRepository, times(1)).getById(any());
        verify(cityRepository, times(1)).delete(any(City.class));
    }

    @Test
    public void whenDeleteCity_returnCityWithValidId() throws ExistEntityException {
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getById(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.deleteById(VALID_CITY_ID);
        assertThat(actualResult.getId()).isEqualTo(VALID_CITY_ID);
    }

    @Test
    public void whenDeleteCity_returnCityWithValidName() throws ExistEntityException {
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getById(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.deleteById(VALID_CITY_ID);
        assertThat(actualResult.getName()).isEqualTo(VALID_CITY_NAME);
    }

    @Test
    public void whenDeleteCity_returnCityWithValidInfo() throws ExistEntityException {
        City returnedCity = getValidCity();
        Optional<City> optionalReturnedCity = Optional.of(returnedCity);
        when(cityRepository.getById(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.deleteById(VALID_CITY_ID);
        assertThat(actualResult.getInfo()).isEqualTo(VALID_CITY_INFO);
    }

    @Test
    public void whenDeleteNotExistentCity_returnExistEntityException() throws ExistEntityException {
        when(cityRepository.getById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(ExistEntityException.class)
                .isThrownBy(() -> cityService.deleteById(any()));
    }

    /* update city by id */
    @Test
    public void whenUpdateCityById_returnNotNull() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(optionalReturnedCity);
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        CityDTO actualResult = cityService.updateById(VALID_CITY_ID, updateCityDTO);
        assertThat(actualResult).isNotNull();
    }

    @Test
    public void whenUpdateCityById_returnCityDTO() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(optionalReturnedCity);
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        CityDTO actualResult = cityService.updateById(VALID_CITY_ID, updateCityDTO);
        assertThat(actualResult).isInstanceOf(CityDTO.class);
    }

    @Test
    public void whenUpdateCityById_callLogic() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(optionalReturnedCity);
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        cityService.updateById(VALID_CITY_ID, updateCityDTO);
        verify(cityRepository, times(1)).getById(any());
        verify(cityRepository, times(1)).update(any(City.class));
    }

    @Test
    public void whenUpdateNotExistentCity_returnExistEntityException() throws ExistEntityException {
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(Optional.empty());
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        assertThatExceptionOfType(ExistEntityException.class)
                .isThrownBy(() -> cityService.updateById(VALID_CITY_ID, updateCityDTO));
    }

    @Test
    public void whenUpdateCityById_returnCityWithValidId() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(optionalReturnedCity);
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        CityDTO actualResult = cityService.updateById(VALID_CITY_ID, updateCityDTO);
        assertThat(actualResult.getId()).isEqualTo(VALID_CITY_ID);
    }

    @Test
    public void whenUpdateCityById_returnCityWithValidNewName() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(optionalReturnedCity);
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        String newName = "newTestName";
        updateCityDTO.setName(newName);
        CityDTO actualResult = cityService.updateById(VALID_CITY_ID, updateCityDTO);
        assertThat(actualResult.getName()).isEqualTo(newName);
    }

    @Test
    public void whenUpdateCityById_returnCityWithValidNewInfo() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getById(VALID_CITY_ID)).thenReturn(optionalReturnedCity);
        UpdateCityDTO updateCityDTO = getValidUpdateCityDTO();
        String newInfo = "new test info for newTestName";
        updateCityDTO.setInfo(newInfo);
        CityDTO actualResult = cityService.updateById(VALID_CITY_ID, updateCityDTO);
        assertThat(actualResult.getInfo()).isEqualTo(newInfo);
    }

    /* find by name */
    @Test
    public void whenFindCityByName_returnNotNull() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getByName(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.findByName(VALID_CITY_NAME);
        assertThat(actualResult).isNotNull();
    }

    @Test
    public void whenFindCityByName_returnCityDTO() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getByName(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.findByName(VALID_CITY_NAME);
        assertThat(actualResult).isInstanceOf(CityDTO.class);
    }

    @Test
    public void whenFindCityByName_callLogic() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getByName(any())).thenReturn(optionalReturnedCity);
        cityService.findByName(VALID_CITY_NAME);
        verify(cityRepository, times(1)).getByName(VALID_CITY_NAME);
    }

    @Test
    public void whenFindCityByName_returnCityWithValidId() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getByName(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.findByName(VALID_CITY_NAME);
        assertThat(actualResult.getId()).isEqualTo(VALID_CITY_ID);
    }

    @Test
    public void whenFindCityByName_returnCityWithValidName() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getByName(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.findByName(VALID_CITY_NAME);
        assertThat(actualResult.getName()).isEqualTo(VALID_CITY_NAME);
    }

    @Test
    public void whenFindCityByName_returnCityWithValidInfo() throws ExistEntityException {
        Optional<City> optionalReturnedCity = Optional.of(getValidCity());
        when(cityRepository.getByName(any())).thenReturn(optionalReturnedCity);
        CityDTO actualResult = cityService.findByName(VALID_CITY_NAME);
        assertThat(actualResult.getInfo()).isEqualTo(VALID_CITY_INFO);
    }

    @Test
    public void whenFindNotExistentCityByName_returnExistEntityException() throws ExistEntityException {
        when(cityRepository.getByName(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(ExistEntityException.class)
                .isThrownBy(() -> cityService.findByName(VALID_CITY_NAME));
    }

    @Test
    public void whenGetAllCities_returnNotNull() {
        List<CityDTO> actualResult = cityService.getAllCities();
        assertThat(actualResult).isNotNull();
    }

    @Test
    public void whenGetAllCities_returnListOfCityDTO() {
        List<City> returnedCities = getValidCities();
        when(cityRepository.findAll()).thenReturn(returnedCities);
        List<CityDTO> actualResult = cityService.getAllCities();
        assertThat(actualResult.get(0)).isInstanceOf(CityDTO.class);
    }

    @Test
    public void whenGetAllCities_callLogic() {
        cityService.getAllCities();
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void whenGetAllCities_returnCitiesWithValidId() {
        List<City> returnedCities = getValidCities();
        when(cityRepository.findAll()).thenReturn(returnedCities);
        List<CityDTO> actualResult = cityService.getAllCities();
        assertThat(actualResult.get(0).getId()).isEqualTo(VALID_CITY_ID);
    }

    @Test
    public void whenGetAllCities_returnCitiesWithValidName() {
        List<City> returnedCities = getValidCities();
        when(cityRepository.findAll()).thenReturn(returnedCities);
        List<CityDTO> actualResult = cityService.getAllCities();
        assertThat(actualResult.get(0).getName()).isEqualTo(VALID_CITY_NAME);
    }

    @Test
    public void whenGetAllCities_returnCitiesWithValidInfo() {
        List<City> returnedCities = getValidCities();
        when(cityRepository.findAll()).thenReturn(returnedCities);
        List<CityDTO> actualResult = cityService.getAllCities();
        assertThat(actualResult.get(0).getInfo()).isEqualTo(VALID_CITY_INFO);
    }

    private List<City> getValidCities() {
        List<City> cities = new ArrayList<>();
        cities.add(getValidCity());
        return cities;
    }

    private UpdateCityDTO getValidUpdateCityDTO() {
        UpdateCityDTO city = new UpdateCityDTO();
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

    private City getValidCity() {
        City city = new City();
        city.setId(VALID_CITY_ID);
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

    private AddCityDTO getValidAddCityDTO() {
        AddCityDTO city = new AddCityDTO();
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

}