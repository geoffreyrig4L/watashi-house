package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

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

    /**@ManyToMany(
            mappedBy = "sousCategories"
    )
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();*/

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity=Categorie.class
    )
    @JoinColumn(name="categorie_id", nullable = false)
    @JsonIgnore
    private Categorie categorie;
}
