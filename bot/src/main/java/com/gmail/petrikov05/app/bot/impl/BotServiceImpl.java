package com.gmail.petrikov05.app.bot.impl;

import java.lang.invoke.MethodHandles;

import com.gmail.petrikov05.app.bot.BotService;
import com.gmail.petrikov05.app.service.CityService;
import com.gmail.petrikov05.app.service.exception.ExistEntityException;
import com.gmail.petrikov05.app.service.model.CityDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BotServiceImpl implements BotService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final CityService cityService;

    public BotServiceImpl(CityService cityService) {this.cityService = cityService;}

    @Override
    public String getMessage(String text) {
        return (text.charAt(0) == "/".charAt(0)) ? getMessageByCommand(text) : getCity(text);
    }

    private String getCity(String cityName) {
        try {
            CityDTO city = cityService.findByName(cityName);
            logger.info("show city info: " + cityName);
            return city.getInfo();
        } catch (ExistEntityException e) {
            logger.info("city with name {} not found", cityName);
            return "About this city I dont know!";
        }
    }

    private String getMessageByCommand(String command) {
        switch (command.toLowerCase()) {
            case "/start": {
                return getMessageForCommandStart();
            }
            case "/help": {
                return getMessageForCommandHelp();
            }
            default: {
                return getMessageForNonFoundCommand();
            }

        }
    }

    private String getMessageForCommandHelp() {
        return "Нou write the name of the city and if I know it then I will write you about it";
    }

    private String getMessageForNonFoundCommand() {
        return " I know only command: /start and /help";
    }

    private String getMessageForCommandStart() {
        return getMessageForCommandHelp();
    }

}
