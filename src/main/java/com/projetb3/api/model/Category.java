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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_categorie")
    private int id;

    @Column(name="nom")
    private String name;

    @ManyToMany(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name = "categories_articles",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "categorie_id")}
    )
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "categorie_id")
    @JsonIgnore
    private List<SubCategory> subCategories = new ArrayList<>();

    @ManyToMany(
            mappedBy = "subCategories"
    )
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();
}
