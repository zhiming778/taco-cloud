package com.example.tacocloud.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity(name = "taco")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @OneToMany
    private List<Ingredient> ingredients;


}