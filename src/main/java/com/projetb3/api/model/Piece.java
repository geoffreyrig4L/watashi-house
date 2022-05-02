package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="pieces")
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piece")
    private int id;

    @Column(name = "nom")
    private String nom;

    /**@ManyToMany(
            mappedBy = "pieces"
    )
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();*/

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name="pieces_souscategories",
            joinColumns = { @JoinColumn(name = "piece_id") },
            inverseJoinColumns = { @JoinColumn(name = "souscategorie_id") }
    )
    @JsonIgnore
    private List<SousCategorie> sousCategories = new ArrayList<>();
}