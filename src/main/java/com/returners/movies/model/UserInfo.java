package com.returners.movies.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class UserInfo {

    @Id
    @GeneratedValue
    @Column(name="user_id", updatable = false, nullable = false)
    private int userId;

    @Column
    private int age;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name="phone_number")
    private String phoneNumber;
}
