package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Collection.class
    )
    @JoinColumn(name = "collection_id")
    @JsonIgnore
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
            mappedBy = "items"
    )
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany(
            mappedBy = "items"
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(
            mappedBy = "items"
    )
    @JsonIgnore
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

