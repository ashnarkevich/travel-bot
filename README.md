# Travel bot.
- Multi-module maven project 
- Version Control System - Git
# Technologies:
- Java 8 
- Spring boot starter 
- Log4j2 
- MySQL 8 
- Telegram Bots 
* Unit and Integration application tests
* Using Liquibase for Migration

#For the project start it is required:
    - JDK8
    - MySQL8.0

add your database settings

```
spring.datasource.username=
spring.datasource.password=
```

does not need to be changed

```
telegram.bot.name=ASTravelBot
telegram.bot.token=1855462666:AAGYg-GAmqowZ-0dk4Mej14gSJTZCF745vk
```

REST api endpoints:

* get      /api/cities        - get all cities
* post     /api/cities        - add new city
* put      /api/cities/{id}   - update city
* delete   /api/cities/{id}   - delete city

For add new city json (example):
{
"name": "brest",
"info": "the best city"
}
