package com.projetb3.api.controller;

import com.projetb3.api.model.Order;
import com.projetb3.api.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/commandes")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getAll() {
        Iterable<Order> ordersList = orderService.getAll();
        return ResponseEntity.ok(ordersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable("id") final int id) {
        Optional<Order> order = orderService.get(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> processToCreation(@RequestBody Order order) {
        fillFields(order);
        return create(order);
    }

    private void fillFields(Order order) {
        order.fillFields();
    }

    private ResponseEntity<String> create(Order order) {
        if (canItCreate(order)) {
            orderService.save(order);
            return ResponseEntity.ok().body("La commande a été créée.");
        }
        return ResponseEntity.badRequest().body("Veuillez entrer une requete valide.");
    }

    private boolean canItCreate(Order order) {
        return !order.getItems().isEmpty() &&
                order.getUser().getId() > 0;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<Order> optOrder = orderService.get(id);
        if (optOrder.isPresent()) {
            orderService.delete(id);
            return ResponseEntity.ok().body("La commande a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Order modified) {
        Optional<Order> optOrder = orderService.get(id);
        if (optOrder.isPresent()) {
            Order current = optOrder.get();
            if (modified.getNumber() != null) {
                current.setNumber(modified.getNumber());
            }
            if (modified.getTotalPrice() != 0) {
                current.setTotalPrice(modified.getTotalPrice());
            }
            orderService.save(current);
            return ResponseEntity.ok().body("La commande " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }
}



