package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="pieces")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piece")
    private int id;

    @Column(name = "nom")
    private String name;

    /**@ManyToMany(
            mappedBy = "pieces"
    )
    @JsonIgnore
    private Set<Item> items = new HashSet<>();*/

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
    private List<SubCategory> subCategories = new ArrayList<>();
}