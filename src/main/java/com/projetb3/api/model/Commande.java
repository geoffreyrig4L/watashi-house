package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.cglib.core.Local;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_commande")
    private int id;

    @Column(name="numero")
    private String numero;

    @Column(name="date_achat")
    private LocalDateTime date_achat;

    @Column(name="prix_tot")
    private int prix_tot;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity=Utilisateur.class
    )
    @JoinColumn(name="utilisateur_id")
    @JsonBackReference
    private Utilisateur utilisateur;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="articles_commandes",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    @JsonIgnore
    private List<Article> articles = new ArrayList<>();
}
