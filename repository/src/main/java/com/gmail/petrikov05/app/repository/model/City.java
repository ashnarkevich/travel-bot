package com.gmail.petrikov05.app.repository.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "db_city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;
    @Column
    private String name;
    @Column
    private String info;

}
