package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "articles")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_article")
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "details")
    private String description;

    @Column(name = "image1")
    private String image1;

    @Column(name = "image2")
    private String image2;

    @Column(name = "image3")
    private String image3;

    @Column(name = "image4")
    private String image4;

    @Column(name = "couleur")
    private String color;

    @Column(name = "prix")
    private int price;

    @Column(name = "stock")
    private int stock;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Collection.class
    )
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @OneToMany(
            targetEntity= Opinion.class,
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Opinion> opinions = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "categories_articles",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "categorie_id")}
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name="pieces_articles",
            joinColumns = { @JoinColumn(name = "article_id")} ,
            inverseJoinColumns = { @JoinColumn(name = "piece_id") }
    )
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name="souscategories_articles",
            joinColumns = { @JoinColumn(name = "article_id") },
            inverseJoinColumns = { @JoinColumn(name = "souscategorie_id") }
    )
    private List<SubCategory> subCategories = new ArrayList<>();

    @ManyToMany(
            cascade = CascadeType.MERGE,
            mappedBy = "items"
    )
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(
            cascade = CascadeType.MERGE,
            mappedBy = "items"
    )
    @JsonBackReference
    private List<Bucket> buckets = new ArrayList<>();
}

