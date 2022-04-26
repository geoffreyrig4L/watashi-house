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
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_utilisateur")
    private int id;

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

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "codepostal")
    private String codepostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "pays")
    private String pays;

    @Column(name = "typeuser")
    private String typeuser;

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

    public Utilisateur(String civilite, String nom, String prenom, String email, String mdp, String tel, String adresse, String pays) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.adresse = adresse;
        this.pays = pays;
    }

    public Utilisateur() {

    }
}