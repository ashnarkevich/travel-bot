package com.gmail.petrikov05.app.service.constant;

import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_INFO_MAX;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_INFO_MIN;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_NAME_MAX;
import static com.gmail.petrikov05.app.service.constant.CityRules.SIZE_NAME_MIN;

public interface CityMessages {

    String MESSAGE_NAME_NOT_NULL = "The name must not be empty";
    String MESSAGE_NAME_NOT_EMPTY = "The name must not be empty and contain only spaces";
    String MESSAGE_NAME_SIZE = "The name must have between " + SIZE_NAME_MIN + " and " + SIZE_NAME_MAX + " symbols";

    String MESSAGE_INFO_NOT_NULL = "The info must not be empty";
    String MESSAGE_INFO_NOT_EMPTY = "The info must not be empty and contain only spaces";
    String MESSAGE_INFO_SIZE = "The info must have between " + SIZE_INFO_MIN + " and " + SIZE_INFO_MAX + " symbols";

    String MESSAGE_CITY_ALREADY_EXIST = "The city with the same already exists";

    String MESSAGE_CITY_NOT_FOUND = "The city with tha same id not found";

}
