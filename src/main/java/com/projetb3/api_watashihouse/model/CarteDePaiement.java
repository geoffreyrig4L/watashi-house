package com.projetb3.api_watashihouse.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="carte_de_paiement")
public class CarteDePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @JoinColumn(name="id_utilisateur_carte", nullable = false)
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
