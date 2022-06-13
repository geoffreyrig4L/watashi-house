package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "avis")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private int id;

    @Column(name = "note")
    private int note;

    @Column(name = "commentaire")
    private String comment;

    @Column(name= "datecreation")
    private LocalDateTime dateOfPublication;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity= Item.class
    )
    @JoinColumn(name="article_id", nullable = false)
    @JsonBackReference
    private Item item;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity= User.class
    )
    @JoinColumn(name="utilisateur_id")
    private User user;
}
