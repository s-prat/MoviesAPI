package com.returners.movies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="Certification")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Certification {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

}