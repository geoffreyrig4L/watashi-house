package com.projetb3.api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="pieces")
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private int id;

    @Column(name = "nom")
    private String nom;
}