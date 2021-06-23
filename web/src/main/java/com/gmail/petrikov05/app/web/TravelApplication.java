package com.gmail.petrikov05.app.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication(
        scanBasePackages = {"com.gmail.petrikov05.app.web",
                "com.gmail.petrikov05.app.service",
                "com.gmail.petrikov05.app.repository",
                "com.gmail.petrikov05.app.bot"
        }
)
public class TravelApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TravelApplication.class, args);
    }

}
