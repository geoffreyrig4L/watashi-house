package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_article")
    private int id;

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
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "categorie_articles",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "categorie_id")}
    )
    private Set<Categorie> categories = new HashSet<>();

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Collection.class
    )
    @JoinColumn(name = "id_collection_article")
    @JsonBackReference
    private Collection collection;


}
