package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "paniers")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier")
    private int id;

    @Column(name = "prix")
    private int price;

    @OneToOne(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinColumn(name="utilisateur_id")
    @JsonBackReference
    private User user;

    @ManyToMany(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name = "paniers_articles",
            joinColumns = @JoinColumn(name = "panier_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Item> items = new ArrayList<>();
}


