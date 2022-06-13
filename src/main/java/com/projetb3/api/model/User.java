package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.codec.binary.Hex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "utilisateurs")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_utilisateur")
    private int id;

    @Column(name = "civilite")
    private String gender;

    @Column(name = "nom")
    private String lastname;

    @Column(name = "prenom")
    private String firstname;

    @Column(name = "email")
    private String email;

    @Column(name = "mdphash")
    private String hash;

    @Column(name = "mdpsalt")
    private String salt;

    @Column(name = "tel")
    private String phone;

    @Column(name = "adresse")
    private String address;

    @Column(name = "codepostal")
    private String zipCode;

    @Column(name = "ville")
    private String city;

    @Column(name = "pays")
    private String country;

    @Column(name = "typeuser")
    private String typeUser;

    @OneToMany(
            targetEntity= DebitCard.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    List<DebitCard> debitCards = new ArrayList<>();

    @OneToMany(
            targetEntity= Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    List<Order> orders = new ArrayList<>();

    @OneToMany(
            targetEntity= Opinion.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Opinion> opinions = new ArrayList<>();

    @OneToOne(
            mappedBy = "user"
    )
    @JsonIgnore
    private Cart cart;

    public void setSalt(byte[] salt){
        this.salt = Hex.encodeHexString(salt);
    }
}