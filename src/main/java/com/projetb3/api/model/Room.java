package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="pieces")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piece")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToMany(
            mappedBy = "rooms"
    )
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    @ManyToMany(
            mappedBy = "rooms"
    )
    @JsonIgnore
    private List<SubCategory> subCategories = new ArrayList<>();

    @ManyToMany(
            mappedBy = "rooms"
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();
}