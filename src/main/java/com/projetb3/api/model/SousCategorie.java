package com.projetb3.api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="sous_categories")
public class SousCategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_souscategorie")
    private int id;

    @Column(name = "nom")
    private String nom;
}
