package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private User user;
}
