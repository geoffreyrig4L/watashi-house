package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "avis")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private int id;

    @Column(name = "note")
    private Float note;

    @Column(name = "commentaire")
    private String comment;

    @Column(name= "datecreation")
    private String dateOfPublication;

    @ManyToOne(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH
            },
            targetEntity= Item.class
    )
    @JoinColumn(name="article_id")
    @JsonBackReference
    private Item item;

    @ManyToOne(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH
            },
            targetEntity= User.class
    )
    @JoinColumn(name="utilisateur_id")
    @JsonIgnoreProperties({"gender","email", "hash","salt","phone","address","zipCode","city","country","typeUser"})
    private User user;
}
