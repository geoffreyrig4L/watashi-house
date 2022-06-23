package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
            mappedBy = "categories"
    )
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    @OneToMany(
            mappedBy = "category"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<SubCategory> subCategories = new ArrayList<>();

    @ManyToMany(
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name="pieces_categories",
            joinColumns = { @JoinColumn(name = "categorie_id") },
            inverseJoinColumns = { @JoinColumn(name = "piece_id") }
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Room> rooms = new ArrayList<>();
}
