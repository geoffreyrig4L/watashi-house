package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_article;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "images")
    private String images;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "prix")
    private int prix;

    @Column(name = "nb_avis")
    private int nb_avis;

    @Column(name = "note")
    private int note;

    @Column(name = "stock")
    private int stock;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "article_categorie",
            joinColumns = @JoinColumn(name = "id_article"),
            inverseJoinColumns = @JoinColumn(name = "id_categorie")
    )
    @JsonIgnore
    private List<Categorie> categories = new ArrayList<>();

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Collection.class
    )
    @JoinColumn(name = "id_collection_article")
    @JsonBackReference
    private Collection collection;


}
