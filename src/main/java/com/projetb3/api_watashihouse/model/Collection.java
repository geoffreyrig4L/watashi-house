package com.projetb3.api_watashihouse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "Collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(
            targetEntity = Article.class,
            mappedBy = "collection",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    Set<Article> articles = new HashSet<>();
}
