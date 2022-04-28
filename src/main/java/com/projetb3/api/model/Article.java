package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "details")
    private String description;

    @Column(name = "images1")
    private String images1;

    @Column(name = "images2")
    private String images2;

    @Column(name = "images3")
    private String images3;

    @Column(name = "images4")
    private String images4;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "prix")
    private int prix;

    @Column(name = "stock")
    private int stock;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Collection.class
    )
    @JoinColumn(name = "collection_id")
    @JsonBackReference
    private Collection collection;

    @OneToMany(
            targetEntity=Avis.class,
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Avis> avis = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "categories_articles",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "categorie_id")}
    )
    private Set<Categorie> categories = new HashSet<>();

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="pieces_articles",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "piece_id")
    )
    @JsonIgnore
    private Set<Piece> pieces = new HashSet<>();

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="souscategories_articles",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "souscategorie_id")
    )
    @JsonIgnore
    private Set<SousCategorie> sousCategories = new HashSet<>();

}
