package com.gmail.petrikov05.app.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.petrikov05.app.service.CityService;
import com.gmail.petrikov05.app.service.exception.ExistEntityException;
import com.gmail.petrikov05.app.service.model.AddCityDTO;
import com.gmail.petrikov05.app.service.model.CityDTO;
import com.gmail.petrikov05.app.service.model.UpdateCityDTO;
import com.gmail.petrikov05.app.web.constant.TestConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_CITY_NOT_FOUND;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_INFO_NOT_EMPTY;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_INFO_NOT_NULL;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_INFO_SIZE;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_NAME_NOT_EMPTY;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_NAME_NOT_NULL;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_NAME_SIZE;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_ID;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_ID_STR;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_INFO;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_NAME;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CityAPIController.class)
@ActiveProfiles("test")
class CityAPIControllerTest {

    private static final String URL_ADD_CITY = "/api/cities";
    private static final String URL_DELETE_CITY = "/api/cities/123e4567-e89b-12d3-a456-426614174000";
    private static final String URL_GET_CITIES = "/api/cities";
    private static final String URL_UPDATE_CITY = "/api/cities/123e4567-e89b-12d3-a456-426614174000";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CityService cityService;

    @Test
    public void whenAddCity_returnStatusCreated() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isCreated());
    }

    @Test
    public void whenAddCity_returnJSON() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.add(any())).thenReturn(returnedCity);
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenAddCity_returnStatusBadRequest() throws Exception {
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithNullName_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(null);
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithNullName_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(null);
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_NOT_NULL)));
    }

    @Test
    public void whenAddCityWithEmptyName_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(" ");
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithEmptyName_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(" ");
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_NOT_EMPTY)));
    }

    @Test
    public void whenAddCityWithSmallName_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(getStringOfSpecificLength(0));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithSmallName_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(getStringOfSpecificLength(0));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_SIZE)));
    }

    @Test
    public void whenAddCityWithLongName_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(getStringOfSpecificLength(171));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithLongName_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setName(getStringOfSpecificLength(171));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_SIZE)));
    }

    @Test
    public void whenAddCityWithNullInfo_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(null);
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithNullInfo_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(null);
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_NOT_NULL)));
    }

    @Test
    public void whenAddCityWithEmptyInfo_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(" ");
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithEmptyInfo_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(" ");
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_NOT_EMPTY)));
    }

    @Test
    public void whenAddCityWithSmallInfo_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(getStringOfSpecificLength(4));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithSmallInfo_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(getStringOfSpecificLength(4));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_SIZE)));
    }

    @Test
    public void whenAddCityWithLongInfo_returnStatusBadRequest() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(getStringOfSpecificLength(257));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddCityWithLongInfo_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        addCityDTO.setInfo(getStringOfSpecificLength(257));
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_SIZE)));
    }

    @Test
    public void whenAddValidCity_callBusinessLogic() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isCreated());
        verify(cityService, times(1)).add(any(AddCityDTO.class));
    }

    @Test
    public void whenAddValidCity_returnCityWithValidUniqueNumber() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.add(any())).thenReturn(returnedCity);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(String.valueOf(VALID_CITY_ID))));
    }

    @Test
    public void whenAddValidCity_returnCityWithValidName() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.add(any())).thenReturn(returnedCity);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(VALID_CITY_NAME)));
    }

    @Test
    public void whenAddValidCity_returnCityWithValidInfo() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.add(any())).thenReturn(returnedCity);
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    @Test
    public void whenAddAlreadyExistCity_returnBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        when(cityService.add(any())).thenThrow(new ExistEntityException("already exist"));
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenAddAlreadyExistCity_returnErrorMessage() throws Exception {
        String body = objectMapper.writeValueAsString(getValidAddCityDTO());
        String message = "already exist";
        when(cityService.add(any())).thenThrow(new ExistEntityException(message));
        mockMvc.perform(post(URL_ADD_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(message)));
    }

    /* delete city by unique number */
    @Test
    public void deleteCity_returnStatusOk() throws Exception {
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(status().isOk());
    }

    @Test
    public void deleteCity_returnCityDTO() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.deleteById(any())).thenReturn(returnedCity);
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteCity_callBusinessLogic() throws Exception {
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(status().isOk());
        verify(cityService, times(1)).deleteById(any());
    }

    @Test
    public void deleteCity_returnCityWithValidId() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.deleteById(any())).thenReturn(returnedCity);
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(content().string(containsString(VALID_CITY_ID_STR)));
    }

    @Test
    public void deleteCity_returnCityWithValidName() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.deleteById(any())).thenReturn(returnedCity);
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(content().string(containsString(VALID_CITY_NAME)));
    }

    @Test
    public void deleteCity_returnCityWithValidInfo() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.deleteById(any())).thenReturn(returnedCity);
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    @Test
    public void deleteNotExistentCity_returnStatusNotFound() throws Exception {
        when(cityService.deleteById(any())).thenThrow(new ExistEntityException());
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void deleteNotExistentCity_returnErrorMessage() throws Exception {
        when(cityService.deleteById(any())).thenThrow(new ExistEntityException());
        mockMvc.perform(delete(URL_DELETE_CITY)
        ).andExpect(content().string(containsString(MESSAGE_CITY_NOT_FOUND)));
    }

    @Test
    public void whenUpdateCity_returnStatusOk() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenUpdateCity_returnJson() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.updateById(any(), any())).thenReturn(returnedCity);
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenUpdateInvalidCity_returnBadRequest() throws Exception {
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithNullName_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName(null);
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithNullName_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName(null);
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_NOT_NULL)));
    }

    @Test
    public void whenUpdateCityWithEmptyName_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName("  ");
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithEmptyName_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName("  ");
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_NOT_EMPTY)));
    }

    @Test
    public void whenUpdateCityWithSmallName_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName(getStringOfSpecificLength(0));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithSmallName_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName(getStringOfSpecificLength(0));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_SIZE)));
    }

    @Test
    public void whenUpdateCityWithLongName_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName(getStringOfSpecificLength(171));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithLongName_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setName(getStringOfSpecificLength(171));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_NAME_SIZE)));
    }

    @Test
    public void whenUpdateCityWithNullInfo_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo(null);
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithNullInfo_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo(null);
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_NOT_NULL)));
    }

    @Test
    public void whenUpdateCityWithEmptyInfo_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo("  ");
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithEmptyInfo_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo("  ");
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_NOT_EMPTY)));
    }

    @Test
    public void whenUpdateCityWithSmallInfo_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo(getStringOfSpecificLength(4));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithSmallInfo_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo(getStringOfSpecificLength(4));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_SIZE)));
    }

    @Test
    public void whenUpdateCityWithLongInfo_returnBadRequest() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo(getStringOfSpecificLength(257));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateCityWithLongInfo_returnErrorMessage() throws Exception {
        UpdateCityDTO updateCity = getValidUpdateCityDTO();
        updateCity.setInfo(getStringOfSpecificLength(257));
        String body = objectMapper.writeValueAsString(updateCity);
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(MESSAGE_INFO_SIZE)));
    }

    @Test
    public void whenUpdateCity_callBusinessLogic() throws Exception {
        String body = objectMapper.writeValueAsString(getValidUpdateCityDTO());
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isOk());
        verify(cityService, times(1)).updateById(any(UUID.class), any(UpdateCityDTO.class));
    }

    @Test
    public void whenUpdateCity_returnCityWithValidId() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.updateById(any(), any())).thenReturn(returnedCity);
        String body = objectMapper.writeValueAsString(getValidUpdateCityDTO());
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(VALID_CITY_ID_STR)));
    }

    @Test
    public void whenUpdateCity_returnCityWithValidName() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.updateById(any(), any())).thenReturn(returnedCity);
        String body = objectMapper.writeValueAsString(getValidUpdateCityDTO());
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(VALID_CITY_NAME)));
    }

    @Test
    public void whenUpdateCity_returnCityWithValidInfo() throws Exception {
        CityDTO returnedCity = getValidCityDTO();
        when(cityService.updateById(any(), any())).thenReturn(returnedCity);
        String body = objectMapper.writeValueAsString(getValidUpdateCityDTO());
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    @Test
    public void whenUpdateNotExistentCity_returnStatusNotFound() throws Exception {
        when(cityService.updateById(any(), any())).thenThrow(new ExistEntityException());
        String body = objectMapper.writeValueAsString(getValidUpdateCityDTO());
        mockMvc.perform(put(URL_UPDATE_CITY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllCities_returnStatusOk() throws Exception {
        mockMvc.perform(get(URL_GET_CITIES)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetAllCities_returnJson() throws Exception {
        mockMvc.perform(get(URL_GET_CITIES)
        ).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetAllCities_callBusinessLogic() throws Exception {
        mockMvc.perform(get(URL_GET_CITIES)
        ).andExpect(status().isOk());
        verify(cityService, times(1)).getAllCities();
    }

    @Test
    public void whenGetAllCities_returnCityWithValidId() throws Exception {
        List<CityDTO> returnedCities = getValidCitiesDTO();
        when(cityService.getAllCities()).thenReturn(returnedCities);
        mockMvc.perform(get(URL_GET_CITIES)
        ).andExpect(content().string(containsString(VALID_CITY_ID_STR)));
    }

    @Test
    public void whenGetAllCities_returnCityWithValidName() throws Exception {
        List<CityDTO> returnedCities = getValidCitiesDTO();
        when(cityService.getAllCities()).thenReturn(returnedCities);
        mockMvc.perform(get(URL_GET_CITIES)
        ).andExpect(content().string(containsString(VALID_CITY_NAME)));
    }

    @Test
    public void whenGetAllCities_returnCityWithValidInfo() throws Exception {
        List<CityDTO> returnedCities = getValidCitiesDTO();
        when(cityService.getAllCities()).thenReturn(returnedCities);
        mockMvc.perform(get(URL_GET_CITIES)
        ).andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    private List<CityDTO> getValidCitiesDTO() {
        List<CityDTO> cities = new ArrayList<>();
        cities.add(getValidCityDTO());
        return cities;
    }

    private UpdateCityDTO getValidUpdateCityDTO() {
        UpdateCityDTO city = new UpdateCityDTO();
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

    private CityDTO getValidCityDTO() {
        CityDTO city = new CityDTO();
        city.setId(TestConstant.VALID_CITY_ID);
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

    private String getStringOfSpecificLength(int length) {
        return IntStream.range(0, length).boxed()
                .map(x -> "a")
                .collect(Collectors.joining());
    }

    private AddCityDTO getValidAddCityDTO() {
        AddCityDTO city = new AddCityDTO();
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

}