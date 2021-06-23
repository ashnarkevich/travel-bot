package com.gmail.petrikov05.app.web.controller;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import com.gmail.petrikov05.app.service.CityService;
import com.gmail.petrikov05.app.service.exception.ExistEntityException;
import com.gmail.petrikov05.app.service.exception.ServiceException;
import com.gmail.petrikov05.app.service.model.AddCityDTO;
import com.gmail.petrikov05.app.service.model.CityDTO;
import com.gmail.petrikov05.app.service.model.UpdateCityDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_CITY_ALREADY_EXIST;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_CITY_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/cities")
public class CityAPIController {

    private final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final CityService cityService;

    public CityAPIController(CityService cityService) {this.cityService = cityService;}

    @GetMapping
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(CREATED)
    public Object addCity(
            @RequestBody @Valid AddCityDTO addCityDTO,
            BindingResult bindingResult
    ) throws ServiceException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getValidMessage(bindingResult);
            return ResponseEntity.status(BAD_REQUEST).body(errorMessage);
        }
        try {
            logger.info("add new city: {}, {}", addCityDTO.getName(), addCityDTO.getInfo());
            return cityService.add(addCityDTO);
        } catch (ExistEntityException e) {
            return ResponseEntity.status(BAD_REQUEST).body(MESSAGE_CITY_ALREADY_EXIST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public Object deleteById(
            @PathVariable UUID id
    ) {
        try {
            logger.info("delete city: {}", id);
            return cityService.deleteById(id);
        } catch (ExistEntityException e) {
            logger.info("city with tha same id ({}) not found", id);
            return ResponseEntity.status(NOT_FOUND).body(MESSAGE_CITY_NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Object updateById(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateCityDTO updateCityDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessages = getValidMessage(bindingResult);
            return ResponseEntity.status(BAD_REQUEST).body(errorMessages);
        }
        try {
            logger.info("update city: {}", id);
            return cityService.updateById(id, updateCityDTO);
        } catch (ExistEntityException e) {
            logger.info("Update unsuccessfully. City with id ({}) not found.", id);
            return ResponseEntity.status(NOT_FOUND).body(MESSAGE_CITY_NOT_FOUND);
        }
    }

    private String getValidMessage(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> message = new ArrayList<>();
        for (FieldError e : errors) {
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }
        return message.toString();
    }

}
