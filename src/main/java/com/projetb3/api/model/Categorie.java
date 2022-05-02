package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_categorie")
    private int id;

    @Column(name="nom")
    private String nom;

    @ManyToMany(
            mappedBy = "categories"
    )
    @JsonIgnore
    private List<Article> articles = new ArrayList<>();
}
