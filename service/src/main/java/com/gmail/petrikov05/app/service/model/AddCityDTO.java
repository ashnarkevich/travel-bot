package com.gmail.petrikov05.app.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_INFO_NOT_EMPTY;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_INFO_NOT_NULL;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_INFO_SIZE;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_NAME_NOT_EMPTY;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_NAME_NOT_NULL;
import static com.gmail.petrikov05.app.service.constant.CityMessages.MESSAGE_NAME_SIZE;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_INFO_MAX;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_INFO_MIN;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_NAME_MAX;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_NAME_MIN;

@Data
public class AddCityDTO {

    @NotNull(message = MESSAGE_NAME_NOT_NULL)
    @NotBlank(message = MESSAGE_NAME_NOT_EMPTY)
    @Size(min = SIZE_NAME_MIN,
            max = SIZE_NAME_MAX,
            message = MESSAGE_NAME_SIZE)
    private String name;
    @NotNull(message = MESSAGE_INFO_NOT_NULL)
    @NotBlank(message = MESSAGE_INFO_NOT_EMPTY)
    @Size(min = SIZE_INFO_MIN,
            max = SIZE_INFO_MAX,
            message = MESSAGE_INFO_SIZE)
    private String info;

}
