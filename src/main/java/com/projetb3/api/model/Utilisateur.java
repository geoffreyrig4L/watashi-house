package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_utilisateur;

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
    List<CarteDePaiement> carteDePaiements = new ArrayList<>();

    @OneToMany(
            targetEntity=Commande.class,
            mappedBy = "utilisateur",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    List<Commande> commandes = new ArrayList<>();

    public Utilisateur(String civilite, String nom, String prenom, String email, String mdp, String tel, String adresse_livraison, String adresse_facturation, String pays) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.adresse_livraison = adresse_livraison;
        this.adresse_facturation = adresse_facturation;
        this.pays = pays;
    }

    public Utilisateur() {

    }
}