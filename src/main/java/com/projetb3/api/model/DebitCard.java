package com.projetb3.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="cartes")
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_carte")
    private int id;

    @Column(name="numero")
    private String number;

    @Column(name="cvc")
    private String cvc;

    @Column(name="annee_expiration")
    private String expiryYear;

    @Column(name="mois_expiration")
    private String expiryMonth;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity= User.class
    )
    @JoinColumn(name="utilisateur_id", nullable = false)
    @JsonBackReference
    private User user;

    public DebitCard() {
    }

    public DebitCard(String numero, String cvc, String expiryYear, String expiryMonth, User user) {
        this.number = numero;
        this.cvc = cvc;
        this.expiryYear = expiryYear;
        this.expiryMonth = expiryMonth;
        this.user = user;
    }
}
