package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "favoris")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favori")
    private int id;

    @OneToOne(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinColumn(name="utilisateur_id")
    private User user;

    @ManyToMany(
            cascade = { CascadeType.MERGE,
                    CascadeType.DETACH }
    )
    @JoinTable(
            name = "favoris_articles",
            joinColumns = @JoinColumn(name = "favori_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Item> items = new ArrayList<>();
}


