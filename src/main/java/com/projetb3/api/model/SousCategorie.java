package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="souscategories")
public class SousCategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_souscategorie")
    private int id;

    @Column(name = "nom")
    private String nom;

//    @ManyToMany(
//            mappedBy = "sousCategories"
//    )
//    @JsonIgnore
//    private Set<Article> articles = new HashSet<>();
}
