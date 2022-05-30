package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="paniers")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_panier")
    private int id;

    @Column(name ="prix")
    private int price;

    @OneToOne
    @JoinColumn(name="utilisateur_id")
    private User user;

    @ManyToMany(
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "paniers_articles",
            joinColumns = @JoinColumn(name = "panier_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    @JsonManagedReference
    private List<Item> items = new ArrayList<>();
}


