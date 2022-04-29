package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "avis")
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private int id;

    @Column(name = "note")
    private int note;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity=Article.class
    )
    @JoinColumn(name="article_id", nullable = false)
    @JsonBackReference
    private Article articlee;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity=Utilisateur.class
    )
    @JoinColumn(name="utilisateur_id")
    @JsonBackReference
    private Utilisateur utilisateur;
}
