package com.projetb3.api_watashihouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;

    //potentiel pb de type
    @Column(name="images")
    private String images;

    @Column(name="couleur")
    private String couleur;

    @Column(name="prix")
    private int prix;

    @Column(name="nb_avis")
    private int nb_avis;

    @Column(name="note")
    private int note;

    @Column(name="stock")
    private int stock;

    @ManyToMany(
            mappedBy = "articles",
            cascade = CascadeType.MERGE,
            targetEntity = Categorie.class
    )
    @JsonIgnore
    private Set<Categorie> categories = new HashSet<>();

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity=Collection.class
    )
    @JoinColumn(name = "id_collection_article")
    @JsonBackReference
    private Collection collection;

}
