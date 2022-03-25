package com.projetb3.api_watashihouse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Utilisateur")
public class Utilisateur {

    //affichage par ID ne marche pas

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "civilite")
    private String civilite;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "mdp")
    private String mdp;

    @Column(name = "tel")
    private String tel;

    @Column(name = "adresse_livraison")
    private String adresse_livraison;

    @Column(name = "adresse_facturation")
    private String adresse_facturation;

    @Column(name = "pays")
    private String pays;

    @Column(name = "type_user")
    private String type_user;

    @OneToMany(
            targetEntity=CarteDePaiement.class,
            mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    Set<CarteDePaiement> carteDePaiements = new HashSet<>();

    @OneToMany(
            targetEntity=Commande.class,
            mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    Set<Commande> commandes = new HashSet<>();

}