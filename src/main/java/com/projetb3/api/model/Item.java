package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

    @Column(name = "note")
    private Float note;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "collection_id")
    //permet de creer un article en lui assignant une collection sans qu'elle soit afficher lors de l'appel de l'article
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name="pieces_articles",
            joinColumns = { @JoinColumn(name = "article_id")} ,
            inverseJoinColumns = { @JoinColumn(name = "piece_id") }
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name="categories_articles",
            joinColumns = { @JoinColumn(name = "article_id")} ,
            inverseJoinColumns = { @JoinColumn(name = "categorie_id") }
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name="souscategories_articles",
            joinColumns = { @JoinColumn(name = "article_id")} ,
            inverseJoinColumns = { @JoinColumn(name = "souscategorie_id") }
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<SubCategory> subCategories = new ArrayList<>();

    @ManyToMany(
            mappedBy = "items"
    )
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(
            mappedBy = "items"
    )
    @JsonIgnore
    private List<Cart> carts = new ArrayList<>();
}

