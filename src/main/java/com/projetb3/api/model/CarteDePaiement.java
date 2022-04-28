package com.projetb3.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="cartes")
public class CarteDePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_carte")
    private int id;

    @Column(name="numero")
    private String numero;

    @Column(name="cvc")
    private String cvc;

    @Column(name="annee_expiration")
    private String annee_expiration;

    @Column(name="mois_expiration")
    private String mois_expiration;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity=Utilisateur.class
    )
    @JoinColumn(name="utilisateur_id", nullable = false)
    @JsonBackReference
    private Utilisateur utilisateur;

    public CarteDePaiement() {
    }

    public CarteDePaiement(String numero, String cvc, String annee_expiration, String mois_expiration, Utilisateur utilisateur) {
        this.numero = numero;
        this.cvc = cvc;
        this.annee_expiration = annee_expiration;
        this.mois_expiration = mois_expiration;
        this.utilisateur = utilisateur;
    }
}
