package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Data
@Entity
@Table(name = "commandes")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private int id;

    @Column(name = "numero")
    private String number;

    @Column(name = "date_achat")
    private LocalDateTime dateOfPurchase;

    @Column(name = "prix_tot")
    private int totalPrice;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = User.class
    )
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference
    private User user;

    @ManyToMany(
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "articles_commandes",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Item> items = new ArrayList<>();

    public void fillFields() {
        number();
        this.setDateOfPurchase(LocalDateTime.now());
    }

    private void number() {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            number.append(nextInt(0, 9));
        }
        this.setNumber(number.toString());
    }
}
