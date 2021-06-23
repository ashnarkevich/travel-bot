package com.gmail.petrikov05.app.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.petrikov05.app.service.model.AddCityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_CITY_ALREADY_EXIST;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_CITY_NOT_FOUND;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_ID;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_ID_STR;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_INFO;
import static com.gmail.petrikov05.app.web.constant.TestConstant.VALID_CITY_NAME;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("/application-integration.properties")
class CityAPIControllerITTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenAddCity_returnValidAddedCityDTO() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        String newName = "new test name";
        addCityDTO.setName(newName);
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post("/api/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isCreated())
                .andExpect(content().string(containsString(newName)))
                .andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    @Test
    public void whenAddAlreadyExistCity_returnErrorMessage() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(post("/api/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_CITY_ALREADY_EXIST)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenUpdateCity_returnUpdatedCity() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(put("/api/cities/" + VALID_CITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isOk())
                .andExpect(content().string(containsString(VALID_CITY_ID_STR)))
                .andExpect(content().string(containsString(VALID_CITY_NAME)))
                .andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    @Test
    public void whenUpdateNotExistentCity_returnNotFound() throws Exception {
        AddCityDTO addCityDTO = getValidAddCityDTO();
        String body = objectMapper.writeValueAsString(addCityDTO);
        mockMvc.perform(put("/api/cities/123e4567-e89b-12d3-a456-426614174333")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isNotFound())
                .andExpect(content().string(containsString(MESSAGE_CITY_NOT_FOUND)));
    }

    @Test
    public void whenDeleteCity_returnDeletedCity() throws Exception {
        mockMvc.perform(delete("/api/cities/" + VALID_CITY_ID)
        ).andExpect(status().isOk())
                .andExpect(content().string(containsString(VALID_CITY_ID_STR)))
                .andExpect(content().string(containsString(VALID_CITY_NAME)))
                .andExpect(content().string(containsString(VALID_CITY_INFO)));
    }

    @Test
    public void whenDeleteNotExistentCity_returnNotFound() throws Exception {
        mockMvc.perform(delete("/api/cities/123e4567-e89b-12d3-a456-426614174333")
        ).andExpect(status().isNotFound())
                .andExpect(content().string(containsString(MESSAGE_CITY_NOT_FOUND)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void whenGetAllCities_returnListCities() throws Exception {
        mockMvc.perform(get("/api/cities")
        ).andExpect(status().isOk())
                .andExpect(content().string(containsString(VALID_CITY_ID_STR)))
                .andExpect(content().string(containsString(VALID_CITY_NAME)))
                .andExpect(content().string(containsString(VALID_CITY_INFO)));
        ;
    }

    private AddCityDTO getValidAddCityDTO() {
        AddCityDTO city = new AddCityDTO();
        city.setName(VALID_CITY_NAME);
        city.setInfo(VALID_CITY_INFO);
        return city;
    }

}