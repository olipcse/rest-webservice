package com.olip.rest.webservices.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 2,message = "Name should have at least 2 characters")
    private String name;
    @Past
    private Date birthDate;
    public User() {

    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
}
