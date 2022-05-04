package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_collection")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany(
            targetEntity = Item.class,
            mappedBy = "collection",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    List<Item> items = new ArrayList<>();
}
