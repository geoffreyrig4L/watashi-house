package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "souscategories")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_souscategorie")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToMany(
            mappedBy = "subCategories"
    )
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "categorie_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Category category;

    @ManyToMany(
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name="pieces_souscategories",
            joinColumns = { @JoinColumn(name = "souscategorie_id") },
            inverseJoinColumns = { @JoinColumn(name = "piece_id") }
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Room> rooms = new ArrayList<>();
}
