package com.projetb3.api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "avis")
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private int id;

    @Column(name = "note")
    private int note;

    @Column(name = "commentaire")
    private String commentaire;
}
