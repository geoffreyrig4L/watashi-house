package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
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
}