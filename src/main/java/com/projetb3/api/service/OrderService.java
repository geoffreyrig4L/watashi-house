package com.projetb3.api.service;

import com.projetb3.api.model.Order;
import com.projetb3.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Optional<Order> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return orderRepository.findById(id);
    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    public void delete(final int id) {
        orderRepository.deleteById(id);
    }

    public Order save(Order order) {           //creer une instance de la table et genere automatiquement l'id
        return orderRepository.save(order);
    }

    public void decrementItemStock(int id_item) {orderRepository.updateStock(id_item);}

    public int getPriceOfItem(int id_item) {
        return orderRepository.getPriceOfItem(id_item);
    }
}
