package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "categorie_id")
    @JsonIgnore
    private List<SousCategorie> sousCategories = new ArrayList<>();

    @ManyToMany(
            mappedBy = "sousCategories"
    )
    @JsonIgnore
    private List<Piece> pieces = new ArrayList<>();
}
