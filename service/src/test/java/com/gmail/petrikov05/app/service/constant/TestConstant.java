package com.gmail.petrikov05.app.service.constant;

import java.util.UUID;

public interface TestConstant {

    String VALID_CITY_NAME = "testCityName";
    String VALID_CITY_INFO = "test city info for testCityName";
    String VALID_CITY_UNIQUE_NUMBER_STR = "123e4567-e89b-12d3-a456-426614174000";
    UUID VALID_CITY_ID = UUID.fromString(VALID_CITY_UNIQUE_NUMBER_STR);

}
