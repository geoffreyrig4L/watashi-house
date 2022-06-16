package com.projetb3.api.controller;

import com.projetb3.api.model.Item;
import com.projetb3.api.model.Order;
import com.projetb3.api.security.AuthenticationWithJWT;
import com.projetb3.api.service.ItemService;
import com.projetb3.api.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.projetb3.api.security.AuthenticationWithJWT.verifyJwt;
import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/commandes")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getAll(@RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<Order> ordersList = orderService.getAll();
            return ResponseEntity.ok(ordersList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Order> order = orderService.get(id);
        if(order.isPresent() && verifySenderOfRequest(token, Optional.of(order.get().getUser().getFirstname()))){
            return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().build();
    }

    /** !!! faire get les commandes d'un user !!! */

    @PostMapping
    public ResponseEntity<String> processToCreation(@RequestBody Order order, @RequestHeader("Authentication") final String token) {
        verifyJwt(token);
        order.fillFields();
        return create(order);
    }

    private ResponseEntity<String> create(Order order) {
        if (canItCreate(order)) {
            decrementItemStock(order);
            orderService.save(order);
            return ResponseEntity.ok().body("La commande a été créée.");
        }
        return ResponseEntity.badRequest().body("Veuillez entrer une requete valide.");
    }

    private void decrementItemStock(Order order) {
        int totalPrice = 0;
        for (Item item : order.getItems()) {
            orderService.decrementItemStock(item.getId());
            totalPrice += orderService.getPriceOfItem(item.getId());
        }
        order.setTotalPrice(totalPrice);
    }

    private boolean canItCreate(Order order) {
        return !order.getItems().isEmpty() &&
                order.getUser().getId() > 0;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Order> optOrder = orderService.get(id);
        if (optOrder.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            orderService.delete(id);
            return ResponseEntity.ok().body("La commande a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id,
                                         @RequestBody Order modified,
                                         @RequestHeader("Authentication") final String token) {
        Optional<Order> optOrder = orderService.get(id);
        if (optOrder.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            Order current = optOrder.get();
            if (modified.getNumber() != null) {
                current.setNumber(modified.getNumber());
            }
            if (modified.getDateOfPurchase() != null) {
                current.setDateOfPurchase(modified.getDateOfPurchase());
            }
            if (modified.getTotalPrice() >= 0) {
                current.setTotalPrice(modified.getTotalPrice());
            }
            orderService.save(current);
            return ResponseEntity.ok().body("La commande " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }
}



