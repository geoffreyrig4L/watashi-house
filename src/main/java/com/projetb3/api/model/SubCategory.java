package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="souscategories")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_souscategorie")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToMany(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name="souscategories_articles",
            joinColumns = { @JoinColumn(name = "article_id") },
            inverseJoinColumns = { @JoinColumn(name = "souscategorie_id") }
    )
    private Set<Item> items = new HashSet<>();

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity= Category.class
    )
    @JoinColumn(name="categorie_id", nullable = false)
    @JsonIgnore
    private Category category;
}
