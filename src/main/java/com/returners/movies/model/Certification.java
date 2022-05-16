package com.returners.movies.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Certification {

    @Id
    @GeneratedValue
    @Column(name = "certification_id", updatable = false, nullable = false)
    private int certificationId;

    @Column
    private String name;
}
